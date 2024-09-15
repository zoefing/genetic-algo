import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

public class Individual {

    /**
     * Chromosome stores the individual's genetic data as an ArrayList of characters
     * Each character represents a gene
     */
    ArrayList<Character> chromosome;

    /**
     * Chooses a letter at random, in the range from A to the number of states indicated
     * @param num_letters The number of letters available to choose from (number of possible states)
     * @return The letter as a Character
     */
    private Character randomLetter(int num_letters) {
        return Character.valueOf((char)(65+ThreadLocalRandom.current().nextInt(num_letters)));
      }
    
    /** 
     * Method to determine whether a given gene will mutate based on the parameter ***m***
     * @param m the mutation rate
     * @return true if a number randomly chosen between 0 and 1 is less than ***m***, else false
    */
    private Boolean doesMutate(float m){
        float randomNum = ThreadLocalRandom.current().nextInt(0, 1);
        return randomNum < m;
    }

    /**
     * Expresses the individual's chromosome as a String, for display purposes
     * @return The chromosome as a String
     */
    public String toString() {
        StringBuilder builder = new StringBuilder(chromosome.size());
        for(Character ch: chromosome) {
          builder.append(ch);
        }
        return builder.toString();
      }

    public int randomRangeThreadLocalRandom(int start, int end) {
      int number = ThreadLocalRandom.current().nextInt(start, end);
      return number;
    }
    
    /** 
     * Initial constructor to generate initial population members
     * @param c_0 The initial chromosome size
     * @param num_letters The number of letters available to choose from
     */
    public Individual(int c_0, int num_letters) {
      // initialize chromosome
      chromosome = new ArrayList<>();
      // create chromosome of size c_0 using randomLetter()
      // using the randomLetter of num_letters states, repeat c_0 times and add to arrayList
      int iteration_tracker = 0;
      for (iteration_tracker = 0; iteration_tracker <= c_0; iteration_tracker++) {
        chromosome.add(randomLetter(c_0));
      }
    }

     /**
      * Second constructor to create parents and offspring chromosomes
      * @param parent1 The first parent chromosome
      * @param parent2 The second parent chromosome
      * @param c_max The maximum chromosome size
      * @param m The chances per round of mutation in each gene
      */
    public Individual(Individual parent1, Individual parent2, int c_max, double m, int num_letters) {
      // create chromosomes for each parent

      // first step of taking the prefix from parent 1: generate a random number <= chromosome length
      int prefixLength = randomRangeThreadLocalRandom(1, c_max);

      // next, initialize index tracker
      int currentPrefixIndex = 0;

      // create a new array list to store parent 1's prefix
      ArrayList<Character> Prefix = new ArrayList<>(); 

      // start at an index of 0 and increase to the random number
      for (currentPrefixIndex = 0; currentPrefixIndex < prefixLength; currentPrefixIndex++) {
        Prefix.add(parent1.chromosome.get(currentPrefixIndex));
      }

      // first step of taking the suffix from parent 2: generate a random number <= chromosome length
      int suffixLength = randomRangeThreadLocalRandom(1, c_max);

      // next, initialize index tracker
      int currentSuffixIndex = (c_max + 1);

      // create a new array list to store parent 2's suffix
      ArrayList<Character> Suffix = new ArrayList<>(); 

      // starting at the end index and decrementing until the length is equal to the random number
      for (currentSuffixIndex = (c_max + 1); currentSuffixIndex > (c_max - suffixLength); currentSuffixIndex--) {
        // reverse order get
        Suffix.add(parent2.chromosome.get(currentSuffixIndex - 1));
      }

      ArrayList<Character> childChromosome = new ArrayList<>();
      childChromosome.addAll(Prefix);
      childChromosome.addAll(Suffix);

      if (childChromosome.size() > c_max) {
        while (childChromosome.size() > c_max) {
          childChromosome.remove((childChromosome.size() - 1));
        }
      }

      // mutation
      for (int gene = 0; gene < childChromosome.size(); gene++) {
        // check if the gene mutates
        if (doesMutate((float) m)) {
          // set gene to a new random letter
          childChromosome.set(gene, randomLetter(num_letters));
        }
        // otherwise
        else {
          ;
        }
      }
    }

    /**
     * Calculates the fitness score of each chromosome
     * @return The fitness score as an int
     */
    public int getFitness() {
      // initialize fitness
      int fitness = 0;

      // fitness test for mirror partners
      for (int gene = 0; gene < chromosome.size(); gene++) {
        Character currentGene = chromosome.get(gene);
        Character mirrorGene = chromosome.get((chromosome.size() - 1 - gene));
        if (currentGene == mirrorGene) {
          fitness += 1;
        }
        else if (currentGene != mirrorGene) {
          fitness -= 1;
        }
      }

      // fitness test for adjacent genes
      for (int gene = 0; gene < chromosome.size(); gene++) {
        if (gene != (chromosome.size() - 1)) {
          Character firstGene = chromosome.get(gene);
          Character nextGene = chromosome.get((gene + 1));
          if (firstGene == nextGene) {
            fitness -= 1;
          }
          else if (firstGene != nextGene) {
            fitness += 0;
          }
        }
        else if (gene == (chromosome.size()) - 1) {
          ; // skip as no following gene
        }
      }

      // remove the return below and write your own
      return fitness;
  }
}
