interface GameState {
  cells: Cell[];
  turn: number;
  winner: number | null; // winner maybe null
  nextAction: number;
}

interface Cell {
  text: string;
  playable: boolean;
  x: number;
  y: number;
  level: number;
  dome: boolean;
}

export type { GameState, Cell }