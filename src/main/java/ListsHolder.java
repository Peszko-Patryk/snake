public interface ListsHolder {
    int sizeOfField = 10;
    Cell[][] cells = new Cell[sizeOfField][sizeOfField];
    Apple apple = new Apple();
    GameField gamefield = new GameField();
    Snake snake = new Snake();
    Game game = new Game();
}
