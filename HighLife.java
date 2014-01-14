public class HighLife {
        public static void main(String[] args) {
        		final int blockSize = 7;
        		final int sleepInterval = 150;
                boolean[][] grid = new boolean[Zen.getZenWidth() / blockSize][Zen.getZenHeight() / blockSize];
                long startTime = System.currentTimeMillis();
                final long fiveMinutes = 1000*60*5;
                //final int seedFactor = 50;
                seed(grid);
                //Zen.flipBuffer();
                load(grid, blockSize);
                //Zen.flipBuffer();
                
     
                while (Zen.isRunning() && System.currentTimeMillis() - startTime < fiveMinutes)
                {
                	boolean[][] next = new boolean[grid.length][grid[0].length];
                	for (int i = 0; i < grid.length; i++)
                	{
                		for (int j = 0; j < grid[0].length; j++)
                		{
                			neighborCheck(grid, next, i, j);
                		}
                	}
                Zen.sleep(sleepInterval);
                grid = next;
                //Zen.flipBuffer();
                load(grid, blockSize);
                //Zen.flipBuffer();
                }
        }
        
        public static void seed(boolean[][] play)
        {
//        	for (int i = 0; i < play.length; i++)
//        	{
//        		for (int j = 0; j < play[0].length; j++)
//        		{
//        			play[i][j] = Math.random() * 100 < factor ? true : false;
//        		}
//        	}
        	int s = (play.length / 2) - 3;
        	play[s+1][s+1] = play[s+2][s+1] = play[s+3][s+1] = play[s+1][s+2] = play[s+4][s+2] = play[s+1][s+3] = play[s+5][s+3] = play[s+2][s+4] = play[s+5][s+4] = play[s+3][s+5] = play[s+4][s+5] = play[s+5][s+5] = true;
        	
        	
        }
        
        public static void neighborCheck(boolean[][] play, boolean[][] copy, int x, int y)
        {
        	int rBound = play.length - 1;
        	int dBound = play[0].length - 1;
        	
        	int upLeft = (x != 0 && y != 0 && play[x-1][y-1] == true) ? 1 : 0;
        	int up = (y != 0 && play[x][y-1] == true) ? 1 : 0;
        	int upRight = (x != rBound && y != 0 && play[x+1][y-1] == true) ? 1 : 0;
        	int right = (x != rBound && play[x+1][y] == true) ? 1 : 0;
        	int dnRight = (x < rBound && y < dBound && play[x+1][y+1] == true) ? 1 : 0;
        	int dn = (y != dBound && play[x][y+1] == true) ? 1 : 0;
        	int dnLeft = (x != 0 && y != dBound && play[x-1][y+1] == true) ? 1 : 0;
        	int left = (x != 0 && play[x-1][y] == true) ? 1 : 0;
        	int neighbors = upLeft + up + upRight + right + dnRight + dn + dnLeft + left;
        	if (play[x][y] == true)
        	{
        		if (neighbors == 3 || neighbors == 6) copy[x][y] = true;
        		else copy[x][y] = false;
        	}
        	else if (neighbors == 2 || neighbors == 3) copy[x][y] = true;
        	else copy[x][y] = false;
        }
        
        public static void load(boolean[][] play, int blockSize)
        {
        	Zen.flipBuffer();
        	Zen.setColor(0, 0, 0);
            Zen.fillRect(0, 0, Zen.getZenWidth(), Zen.getZenHeight());
            for (int i = 0; i < play.length; i++)
        	{
        		for (int j = 0; j < play[0].length; j++)
        		{
        			if (play[i][j] == true)
        			{
        				Zen.setColor(255, 255 * j / play[0].length, 191 + 64 * j / play[0].length);
        				Zen.fillRect(i * blockSize, j * blockSize, blockSize, blockSize);
        			}
        		}
        	}
            Zen.flipBuffer();
        }
        
/* @todo:
 * 
 * Add a refresh function
 * Encode something to test for stasis followed by wiping and reseeding
 * Maybe animate the births/deaths
 */
}
