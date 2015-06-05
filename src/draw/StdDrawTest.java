package draw;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class StdDrawTest {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	
	public static void drawParticles(List<ParticlePoint> particles){
		
		for( ParticlePoint p : particles){
			StdDraw.filledCircle(p.x, p.y, .005);

		}
	}
	
	public static void moveParticles(double x, double y, List<ParticlePoint> particles)
	{
		for ( ParticlePoint p : particles){
			p.x = p.x + x;
			p.y = p.y + y;
		}
	}
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		//StdDraw.square(.5, .5, .5 );
		for ( float i = 0; i < 1.0f; i+= .2){
			for ( float j = 0; j < 1.0; j+= .2){
				StdDraw.square(i+.1, j+.1, .1 );

			}
		}
		

		System.out.println("We out");
		StdDraw.setPenColor(StdDraw.RED);
		
		List<double[]> moves = new ArrayList<double[]>();
		moves.add(new double[]{.1,0});
		moves.add(new double[]{.1,0});
		moves.add(new double[]{.1,0});
		moves.add(new double[]{.1,0});
		moves.add(new double[]{0,.1});
		moves.add(new double[]{0,.1});
		moves.add(new double[]{0,.1});
		moves.add(new double[]{-.1,.1});
		moves.add(new double[]{-.1,-.1});


		float x = .5f;
		float y = .5f;
		
		Random rand = new Random();
		List<ParticlePoint> particles = new ArrayList<ParticlePoint>();
		
		for( int i = 0; i < 1000; i++){
			
			//double rndx = rand.nextGaussian() * .05 + (float)x;
			//double rndy = rand.nextGaussian() * .05 + (float)x;
			double rndx = rand.nextFloat();
			double rndy = rand.nextFloat(); 
			particles.add( new ParticlePoint(rndx, rndy)  );
			
			//System.out.format("X:%f Y:%f\n", rndx, rndy);
			//StdDraw.filledCircle(rndx, rndy, .005);
			
		}
		
		
		drawParticles(particles);
		
		Thread.sleep(1000);
		
		for ( double[] mv : moves){
			StdDraw.clear();

			moveParticles(mv[0], mv[1], particles);
			Thread.sleep(1000);
			drawParticles(particles);
			Thread.sleep(2000);

		}


	}

}
