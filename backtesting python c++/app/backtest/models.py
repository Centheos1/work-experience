from typing import Dict, List


class BacktestResult:

    def __repr__(self):
        return f"""{'-'*80}
Parameters = {self.parameters}
PNL = {round(self.pnl, 2)}
Num Trades = {self.num_trades}
Max. Drawdown = {round(self.max_dd, 5)}
Rank = {self.rank}
Crowding Distance = {round(self.crowding_distance, 5)}
"""

    def __init__(self):
        self.pnl: float = 0.0
        self.max_dd: float = 0.0
        self.num_trades: int = 0
        self.parameters: Dict = dict()
        self.dominated_by: int = 0
        self.dominates: List[int] = []
        self.rank: int = 0
        self.crowding_distance: float = 0.0

    def reset_results(self):
        self.dominated_by = 0
        self.dominates.clear()
        self.rank = 0
        self.crowding_distance = 0.0
