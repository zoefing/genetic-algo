import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.Comparator;

public class GA_Simulation {

  // constructor 

  // init method
  public void init(int numIndividuals, int c_0, int num_letters) {
    // create n individuals
    for (int num = 0; num < numIndividuals; num++) {
      Individual individual = new Individual(c_0, num_letters);
    }
  }

  /** Sorts population by fitness score, best first 
   * @param pop: ArrayList of Individuals in the current generation
   * @return: ArrayList of Individuals sorted by fitness
  */
  public void rankPopulation(ArrayList<Individual> pop) {
    // sort population by fitness
    Comparator<Individual> ranker = new Comparator<Individual>() {
      // this order will sort higher scores at the front
      public int compare(Individual c1, Individual c2) {
        return (int)Math.signum(c2.getFitness() - c1.getFitness());
      }
    };
    pop.sort(ranker); 
  }
}