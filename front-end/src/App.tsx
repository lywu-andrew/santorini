import React from 'react';
import './App.css'; // import the css file to enable your styles.
import { GameState, Cell } from './game';
import BoardCell from './Cell';

/**
 * Define the type of the props field for a React component
 */
interface Props { }

/**
 * Using generics to specify the type of props and state.
 * props and state is a special field in a React component.
 * React will keep track of the value of props and state.
 * Any time there's a change to their values, React will
 * automatically update (not fully re-render) the HTML needed.
 * 
 * props and state are similar in the sense that they manage
 * the data of this component. A change to their values will
 * cause the view (HTML) to change accordingly.
 * 
 * Usually, props is passed and changed by the parent component;
 * state is the internal value of the component and managed by
 * the component itself.
 */
class App extends React.Component<Props, GameState> {
  private initialized: boolean = false;

  /**
   * @param props has type Props
   */
  constructor(props: Props) {
    super(props)
    /**
     * state has type GameState as specified in the class inheritance.
     */
    this.state = { cells: [], turn: 0, winner: null }
  }

  /**
   * Use arrow function, i.e., () => {} to create an async function,
   * otherwise, 'this' would become undefined in runtime. This is
   * just an issue of Javascript.
   */
  newGame = async () => {
    const response = await fetch('/newgame');
    const json = await response.json();
    this.setState(json);
  }

  /**
   * move will generate an anonymous function that the component
   * can bind with.
   * @param x 
   * @param y 
   * @returns 
   */
  placeworkers(x1: number, y1: number, x2: number, y2: number): React.MouseEventHandler {
    return async (e) => {
      // prevent the default behavior on clicking a link; otherwise, it will jump to a new page.
      e.preventDefault();
      const response = await fetch(`/placeworkers?x1=${x1}&y1=${y1}&x2=${x2}&y2=${y2}`)
      const json = await response.json();
      this.setState(json);
    }
  }

  /**
   * move will generate an anonymous function that the component
   * can bind with.
   * @param x 
   * @param y 
   * @returns 
   */
  move(xcurr: number, ycurr: number, xnext: number, ynext: number): React.MouseEventHandler {
    return async (e) => {
      // prevent the default behavior on clicking a link; otherwise, it will jump to a new page.
      e.preventDefault();
      const response = await fetch(`/move?x1=${xcurr}&y1=${ycurr}&x2=${xnext}&y2=${ynext}`)
      const json = await response.json();
      this.setState(json);
    }
  }

  /**
   * build will generate an anonymous function that the component
   * can bind with.
   * @param x 
   * @param y 
   * @returns 
   */
  build(x: number, y: number): React.MouseEventHandler {
    return async (e) => {
      // prevent the default behavior on clicking a link; otherwise, it will jump to a new page.
      e.preventDefault();
      const response = await fetch(`/build?x=${x}&y=${y}`)
      const json = await response.json();
      this.setState(json);
    }
  }

  createCell(cell: Cell, index: number): React.ReactNode {
    if (cell.playable)
      /**
       * key is used for React when given a list of items. It
       * helps React to keep track of the list items and decide
       * which list item need to be updated.
       * @see https://reactjs.org/docs/lists-and-keys.html#keys
       */
      return (
        <div key={index}>
          <a href='/' onClick={this.play(cell.x, cell.y)}>
            <BoardCell cell={cell}></BoardCell>
          </a>
        </div>
      )
    else
      return (
        <div key={index}><BoardCell cell={cell}></BoardCell></div>
      )
  }

  createInstruction(): React.ReactNode {
    if (this.state.winner !== null) {
      return `Player ${this.state.winner} wins!`
    } else {
      return `It is Player ${this.state.turn}'s turn.` // still need to display what is nextAction
    }
  }

  /**
   * This function will call after the HTML is rendered.
   * We update the initial state by creating a new game.
   * @see https://reactjs.org/docs/react-component.html#componentdidmount
   */
  componentDidMount(): void {
    /**
     * setState in DidMount() will cause it to render twice which may cause
     * this function to be invoked twice. Use initialized to avoid that.
     */
    if (!this.initialized) {
      this.newGame();
      this.initialized = true;
    }
  }

  /**
   * The only method you must define in a React.Component subclass.
   * @returns the React element via JSX.
   * @see https://reactjs.org/docs/react-component.html
   */
  render(): React.ReactNode {
    /**
     * We use JSX to define the template. An advantage of JSX is that you
     * can treat HTML elements as code.
     * @see https://reactjs.org/docs/introducing-jsx.html
     */
    return (
      <div>
        <div id="instructions">{this.createInstruction()}</div>
        <div id="board">
          {this.state.cells.map((cell, i) => this.createCell(cell, i))}
        </div>
        <div id="bottombar">
          <button onClick={/* get the function, not call the function */this.newGame}>New Game</button>
        </div>
      </div>
    );
  }
}

export default App;
