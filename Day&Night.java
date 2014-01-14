public class DayAndNight {
        public static void main(String[] args) {
        		final int blockSize = 3;
        		final int sleepInterval = 70;
        		final int colorOffset = 127;
                boolean[][] grid = new boolean[Zen.getZenWidth() / blockSize][Zen.getZenHeight() / blockSize];
                long startTime = System.currentTimeMillis();
                final long fiveMinutes = 1000*60*5;
                final int seedFactor = 50;
                seed(grid, seedFactor);
                //Zen.flipBuffer();
                load(grid, blockSize, colorOffset);
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
                load(grid, blockSize, colorOffset);
                //Zen.flipBuffer();
                }
        }
        
        public static void seed(boolean[][] play, int factor)
        {
        	for (int i = 0; i < play.length; i++)
        	{
        		for (int j = 0; j < play[0].length; j++)
        		{
        			play[i][j] = Math.random() * 100 < factor ? true : false;
        		}
        	}
//        	int startLeft = (play.length / 2) - 5;
//        	int startRight = (play.length / 2) + 5;
//        	int startUp = (play[0].length / 2) - 5;
//        	int startDown = (play[0].length / 2) + 5;
//        	for (int i = startLeft; i <= startRight; i++)
//        	{
//        		for (int j = startUp; j <= startDown; j++)
//        		{
//        			play[i][j] = Math.random() * 100 < factor ? true : false;
//        		}
//        	}
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
        		if (neighbors == 3 || neighbors == 4 || neighbors == 6 || neighbors == 7 || neighbors == 8) copy[x][y] = true;
        		else copy[x][y] = false;
        	}
        	else if (neighbors == 3 || neighbors == 6 || neighbors == 7 || neighbors == 8) copy[x][y] = true;
        	else copy[x][y] = false;
        }
        
        public static void load(boolean[][] play, int blockSize, int offset)
        {
        	Zen.flipBuffer();
        	Zen.setColor(255, 255, 255);
            Zen.fillRect(0, 0, Zen.getZenWidth(), Zen.getZenHeight());
            for (int i = 0; i < play.length; i++)
        	{
        		for (int j = 0; j < play[0].length; j++)
        		{
        			if (play[i][j] == true)
        			{
        				Zen.setColor(31, 166, 157);
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
