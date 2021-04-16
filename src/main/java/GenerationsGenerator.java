public class GenerationsGenerator implements ListsHolder {
    private int snakesInGeneration = 1;
    private Game[] games = new Game[snakesInGeneration];
    private int[] scores = new int[snakesInGeneration];
    private  Cell[][] cells = new Cell[sizeOfField][sizeOfField];

    public GenerationsGenerator() throws InterruptedException {
        createGames();
        while(true) {
            findBestSnake();
            BreadNextGenerationFromBestSnake();
            Thread.sleep(1000);
        }
    }

    private void BreadNextGenerationFromBestSnake() {
        for (int i = 0; i<snakesInGeneration;i++){
            games[i] = new Game(bestGames.get(0).getSnake().getNeuronNetwork());
            scores[i] = games[i].play();
        }
    }

    private void createGames() {
        for (int i = 0; i<snakesInGeneration;i++){
            games[i] = new Game(null);
            scores[i] = games[i].play();
        }
    }

    private void findBestSnake() {
        int j = 0;
        int i, k = 0;
        for (i = 0; i<snakesInGeneration;i++){
            if (scores[i] > j){
                j = scores[i];
                k = i;
            }
        }
        bestGames.add(new Game(games[k].getSnake().getNeuronNetwork()));
    }


}
