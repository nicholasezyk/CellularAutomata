
public class ConwaysGameofLife {
        public static void main(String[] args) {
        		final int blockSize = 20;
        		final int sleepInterval = 70;
        		final int colorOffset = 127;
                boolean[][] grid = new boolean[Zen.getZenWidth() / blockSize][Zen.getZenHeight() / blockSize];
                long startTime = System.currentTimeMillis();
                final long fiveMinutes = 1000*60*5;
                final int seedFactor = 60;
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
        		if (neighbors < 2 || neighbors > 3) copy[x][y] = false;
        		else copy[x][y] = true;
        	}
        	else if (neighbors == 3) copy[x][y] = true;
        }
        
        public static void load(boolean[][] play, int blockSize, int offset)
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
//        				if (i >= 0 && i < play.length / 6)
//        				{
//        					Zen.setColor(255 - offset, offset + ((255 - 2 * offset) * ((i - 0) / play.length / 6)), offset);
//        					Zen.fillRect(i * blockSize, j * blockSize, blockSize, blockSize);
//        				}
//        				else if (i >= play.length / 6 && i < play.length / 3)
//        				{
//        					Zen.setColor(offset + (255 - 2 * offset) - ((255 - 2 * offset) * ((i - play.length / 6) / play.length / 6)), 255 - offset, offset);
//            				Zen.fillRect(i * blockSize, j * blockSize, blockSize, blockSize);
//        				}
//        				else if (i >= play.length / 3 && i < play.length / 2)
//        				{
//        					Zen.setColor(offset, 255 - offset, offset + ((255 - 2 * offset) * ((i - play.length / 3) / play.length / 6)));
//            				Zen.fillRect(i * blockSize, j * blockSize, blockSize, blockSize);
//        				}
//        				else if (i >= play.length / 2 && i < 2 * play.length / 3)
//        				{
//        					Zen.setColor(offset, offset + (255 - 2 * offset) - ((255 - 2 * offset) * ((i - play.length / 2) / play.length / 6)), 255 - offset);
//            				Zen.fillRect(i * blockSize, j * blockSize, blockSize, blockSize);
//        				}
//        				else if (i >= 2 * play.length / 3 && i < 5 * play.length / 6)
//        				{
//        					Zen.setColor(offset + ((255 - 2 * offset) * ((i - 0) / 2 * play.length / 3)), offset, 255 - offset);
//            				Zen.fillRect(i * blockSize, j * blockSize, blockSize, blockSize);
//        				}
//        				else
//        				{
//        					Zen.setColor(offset + (255 - 2 * offset) - ((255 - 2 * offset) * ((i - 5 * play.length / 6) / play.length / 6)), offset, 255 - offset);
//            				Zen.fillRect(i * blockSize, j * blockSize, blockSize, blockSize);
//        				} //bad looking rainbow pattern
        				Zen.setColor(255, 255, 255);
        				Zen.fillRect(i * blockSize, j * blockSize, blockSize, blockSize);
        				Zen.setColor(0,0,0);
        			}
        		}
        	}
            Zen.flipBuffer();
        }
        
/* @todo:
 * 
 * Figure out how to stop the blinking (happens about 1 in every 10 ticks) without making the whole thing black from too many bufferFlip calls.
 * Make the rainbow pattern better
 * Encode something to test for stasis followed by wiping and reseeding
 * Maybe animate the births/deaths
 */
}
