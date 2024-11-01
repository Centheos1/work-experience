import logging
from datetime import datetime, timezone
import pandas as pd

import backtester
import optimiser
from data_collector import collect_all
from exchanges.binance import BinanceClient
from utils import TF_EQUIV

logger = logging.getLogger()
logger.setLevel(logging.DEBUG)

stream_formatter = logging.Formatter("%(asctime)s [%(levelname)s] %(filename)s %(lineno)d :: %(message)s")

stream_handler = logging.StreamHandler()
stream_handler.setFormatter(stream_formatter)
stream_handler.setLevel(logging.INFO)

file_formatter = logging.Formatter("%(asctime)s [%(levelname)s] %(filename)s %(lineno)d :: %(message)s")

file_handler = logging.FileHandler("./logs/info.log")
file_handler.setFormatter(file_formatter)
file_handler.setLevel(logging.DEBUG)

logger.addHandler(stream_handler)
logger.addHandler(file_handler)


if __name__ == "__main__":

    client = None
    exchange = None

    while True:
        mode = input("Choose the program mode (data / backtest / optimise): ").lower()
        if mode in ["data","backtest","optimise"]:
            break

    # Exchange
    while True:
        exchange = input("Choose an exchange: ").lower()

        if exchange == "":
            exchange = "binance"

        if exchange in ["binance"]:
            break

    if exchange == 'binance':
        client = BinanceClient(futures=True)

    # Symbol
    while True:
        symbol = input("Choose a symbol: ").upper()

        if symbol == "":
            symbol = "BTCUSDT"

        if symbol in client.symbols:
            break

    # Data
    if mode == "data":

        # Select from time
        while True:
            from_time = input("Data from (yyyy-mm-dd or Press Enter): ")
            if from_time == "":
                from_time = 0
                break

            try:
                from_time = int(datetime.strptime(from_time, "%Y-%m-%d").timestamp() * 1000)
                break
            except ValueError:
                continue

        collect_all(client, exchange, symbol, from_time)

    # Strategy
    elif mode in ['backtest', "optimise"]:

        available_strategies = ["obv", "ichimoku", "sup_res", "sma", "psar"]

        # Select strategy
        while True:
            strategy = input(f"Choose a strategy ({', '.join(available_strategies)}): ")
            if strategy in available_strategies:
                break

        # Select timeframe
        while True:
            tf = input(f"Choose a timeframe ({', '.join((TF_EQUIV.keys()))}): ").lower()
            if tf in TF_EQUIV.keys():
                break

        # Select from time
        while True:
            from_time = input("Backtest from (yyyy-mm-dd or Press Enter): ")
            if from_time == "":
                from_time = 0
                break

            try:
                from_time = int(datetime.strptime(from_time, "%Y-%m-%d").timestamp() * 1000)
                break
            except ValueError:
                continue

        # Select to time
        while True:
            to_time = input("Backtest from (yyyy-mm-dd or Press Enter): ")
            if to_time == "":
                to_time = int(datetime.now(tz=timezone.utc).timestamp() * 1000)
                break

            try:
                to_time = int(datetime.strptime(to_time, "%Y-%m-%d").timestamp() * 1000)
                break
            except ValueError:
                continue

        if mode == "backtest":
            print(backtester.run(exchange, symbol, strategy, tf, from_time, to_time))
        elif mode == "optimise":

            pop_size = 0
            generations = 0

            # Population Size
            while True:
                try:
                    pop_size = int(input("Choose a population size: "))
                    break
                except ValueError:
                    continue

            # Iterations
            while True:
                try:
                    generations = int(input("Choose the number of generations: "))
                    break
                except ValueError:
                    continue

            nsga2 = optimiser.Nsga2(exchange, symbol, strategy, tf, from_time, to_time, pop_size)

            p_population = nsga2.create_initial_population()
            p_population = nsga2.evaluate_population(p_population)
            p_population = nsga2.crowding_distance(p_population)

            g = 0
            while g < generations:

                q_population = nsga2.create_offspring_population(p_population)
                q_population = nsga2.evaluate_population(q_population)

                r_population = p_population + q_population

                nsga2.population_params.clear()

                i = 0
                population = dict()
                for bt in r_population:
                    bt.reset_results()
                    nsga2.population_params.append(bt.parameters)
                    population[i] = bt
                    i += 1

                fronts = nsga2.non_dominated_sorting(population)
                for j in range(len(fronts)):
                    fronts[j] = nsga2.crowding_distance(fronts[j])

                p_population = nsga2.create_new_population(fronts)

                print(f"\r{int((g + 1) / generations * 100)}%", end='')

                g += 1

            print("\n")

            print(f"exchange: {exchange} | symbol: {symbol} | strategy: {strategy} | timeframe {tf}\n{pd.to_datetime(from_time, unit='ms')} -> {pd.to_datetime(to_time, unit='ms')}\n")

            for i, individual in enumerate(list(p_population)):
                print(f"{i + 1} {individual}")

