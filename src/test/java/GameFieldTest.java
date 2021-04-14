import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameFieldTest implements ListsHolder {

    @Test
    void should_putAppleOnMap(){
        GameField gameField = new GameField();
        boolean result = apple.getCell() != null;
        assertTrue(result);
    }

    @Test
    void should_setCells(){
        GameField gameField = new GameField();
        boolean result = true;
        for (int i = 0; i < sizeOfField; i++){
            for (int j = 0; j < sizeOfField; j++){
                if (cells[i][j] == null){
                    result = false;
                }
            }
        }
        assertTrue(result);
    }
}