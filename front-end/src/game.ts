interface GameState {
  cells: Cell[];
  turn: number;
  winner: number | null; // winner maybe null
  instruction: string;
}

interface Cell {
  text: string;
  playable: boolean;
  x: number;
  y: number;
}

export type { GameState, Cell }