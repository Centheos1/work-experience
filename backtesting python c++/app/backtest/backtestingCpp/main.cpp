#include <iostream>
#include <cstring>

#include "strategies/Sma.h"
#include "strategies/Psar.h"

int main(int, char **)
{

    std::string symbol = "BTCUSDT";
    std::string exchange = "binance";
    // std::string timeframe = "5m";
    std::string timeframe = "1d";

    char *symbol_char = strcpy((char *)malloc(symbol.length() + 1), symbol.c_str());
    char *exchange_char = strcpy((char *)malloc(exchange.length() + 1), exchange.c_str());
    char *tf_char = strcpy((char *)malloc(timeframe.length() + 1), timeframe.c_str());

    Sma sma(exchange_char, symbol_char, tf_char, 0, 1724968400600);
    sma.execute_backtest(15, 8);
    printf("SMA: PNL: %f | Max Drawdown: %f | Num Trades: %d\n", sma.pnl, sma.max_dd, sma.num_trades);

    // Psar psar(exchange_char, symbol_char, tf_char, 0, 1724968400600);
    // psar.execute_backtest(0.02, 0.02, 0.2);
    // printf("PSAR: PNL: %f | Max Drawdown: %f | Num Trades: %d\n", psar.pnl, psar.max_dd, psar.num_trades);

    printf("Done");
}
