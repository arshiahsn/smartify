package com.SmartCity.tsp;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.Random;

import com.SmartCity.smartcity.SmartCity;

public class Particle {
	
	private List<SwapOperator> velocity;
	private List<Integer> pBest;
	private List<Integer> solution;
	private UtilityFunction utilityFunction;
	private Double pBFitness;
	private Random random;
	
	private Double realDist;
	private Double duration;
	private Integer quality;
	private Double serviceTime;
	private Double fitness;
	private Double dist;
	
	private Double realDistUtil;
	private Double durationUtil;
	private Double qualityUtil;

	public Particle(List<String> permute){

		random = new Random();
		utilityFunction = new UtilityFunction();
		solution = new LinkedList<Integer>();
		velocity = new LinkedList<SwapOperator>();

		//Get origin
		int endIndex = ParticleSwarm.mapping.getMap().size()-1;
		solution.add(0);
		//Based on this permutation of services, create a random solution
		for(String entry : permute){
			//Create a list of service provider numbers that has the same service
			List<Integer> nodeNumList = ParticleSwarm.mapping.getMapAssist().get(entry);
			int listSize = nodeNumList.size();
			int listIndex = random.nextInt(listSize);
			int listEntry = nodeNumList.get(listIndex);
			solution.add(listEntry);
		}
		//Get destination
		solution.add(endIndex);
		pBest = new LinkedList<Integer>(solution);
		this.calculateFitness();
		setpBFitness(fitness);
		
	}
	
	public Particle(Particle particle){
		this.setSolution(particle.getSolution());
		this.setFitness(particle.getFitness());
		this.setDuration(particle.getDuration());
		this.setRealDist(particle.getRealDist());
		this.setQuality(particle.getQuality());
		this.setServiceTime(particle.getServiceTime());
		this.setDist(particle.getDist());
		this.setDurationUtil(particle.getDurationUtil());
		this.setRealDistUtil(particle.getRealDistUtil());
		this.setQualityUtil(particle.getQualityUtil());
	}
	
	

	void calculateFitness() {
		// Calculate fitness for the chromosomes
		// for(Chromosome chromosome : chromosomes){
		// Cumulative utility of a chromosome as fitness
		// Duration and distance also used for measurements
		double cumulativeUtil = 0;
		double cumulativeDist = 0;
		double cumulativeRealDist = 0;
		double cumulativeDuration = 0;
		double cumulativeServTime = 0;
		int cumulativeQual = 0;
		
		double cumulativeRealDistUtil = 0;
		double cumulativeDurationUtil = 0;
		double cumulativeQualityUtil = 0;

		List<Integer> nodes = new LinkedList<Integer>(getSolution());
		ListIterator<Integer> liter = nodes.listIterator();
		double utility, realDist, dur, qual, dist, serviceTime,realDistUtil, realDurUtil, qualUtil;
		Integer listIdx = liter.next();
		while (liter.hasNext()) {
			Integer listNextIdx = liter.next();
			if (ParticleSwarm.mapping.getNode(listIdx).getServiceType().equals("start")) {
				// Casting from long to int as ID
				utility = ParticleSwarm.mapping.getRealUtilEle(0, listNextIdx);
				realDist = ParticleSwarm.mapping.getRealDistEle(0, listNextIdx);
				dur = ParticleSwarm.mapping.getRealDurEle(0, listNextIdx);
				qual = ParticleSwarm.mapping.getNode(listNextIdx).getQuality();
				dist = ParticleSwarm.mapping.getDistEle(0, listNextIdx);
				serviceTime = ParticleSwarm.mapping.getServiceTimeEle(0, listNextIdx);
				realDistUtil = ParticleSwarm.mapping.getRealDistUtilEle(0, listNextIdx);
				realDurUtil = ParticleSwarm.mapping.getRealDistUtilEle(0, listNextIdx);
				qualUtil = ParticleSwarm.mapping.getRealQualUtilEle(0, listNextIdx);
			} else if (ParticleSwarm.mapping.getNode(listNextIdx).getServiceType().equals("finish")) {
				utility = ParticleSwarm.mapping.getRealUtilEle(1, listIdx);
				realDist = ParticleSwarm.mapping.getRealDistEle(1, listIdx);
				dur = ParticleSwarm.mapping.getRealDurEle(1, listIdx);
				dist = ParticleSwarm.mapping.getDistEle(1, listIdx);
				realDistUtil = ParticleSwarm.mapping.getRealDistUtilEle(1, listIdx);
				realDurUtil = ParticleSwarm.mapping.getRealDurUtilEle(1, listIdx);
				qualUtil = 0;
				serviceTime = 0;
				qual = 0;
			} else {
				utility = utilityFunction.calculateUtility(ParticleSwarm.mapping.getNode(listIdx), ParticleSwarm.mapping.getNode(listNextIdx)).getUtility();
				realDist = utilityFunction.getAttributes().getRealDist();
				dur = utilityFunction.getAttributes().getDuration();
				qual = ParticleSwarm.mapping.getNode(listNextIdx).getQuality();
				dist = utilityFunction.getAttributes().getDist();
				serviceTime = utilityFunction.getAttributes().getServiceTime();
				realDistUtil = utilityFunction.getAttributes().getRealDistUtil();
				realDurUtil = utilityFunction.getAttributes().getDurationUtil();
				qualUtil = utilityFunction.getAttributes().getQualityUtil();
			}

			cumulativeRealDist += realDist;
			cumulativeDuration += dur;
			cumulativeUtil += utility;
			cumulativeQual += qual;
			cumulativeDist += dist;
			cumulativeServTime += serviceTime;
			cumulativeRealDistUtil += realDistUtil;
			cumulativeDurationUtil += realDurUtil;
			cumulativeQualityUtil += qualUtil;
			listIdx = listNextIdx;
		}
		this.setFitness(cumulativeUtil);
		this.setRealDist(cumulativeRealDist);
		this.setDuration(cumulativeDuration);
		this.setQuality(cumulativeQual);
		this.setDist(cumulativeDist);
		this.setServiceTime(cumulativeServTime);
		this.setRealDistUtil(cumulativeRealDistUtil);
		this.setDurationUtil(cumulativeDurationUtil);
		this.setQualityUtil(cumulativeQualityUtil);

	}


	
	public List<SwapOperator> getVelocity() {
		return velocity;
	}
	public void setVelocity(List<SwapOperator> velocity) {
		this.velocity = velocity;
	}
	public List<Integer> getpBest() {
		return pBest;
	}
	public void setpBest(List<Integer> pBest) {
		this.pBest = pBest;
	}
	public List<Integer> getSolution() {
		return solution;
	}
	public void setSolution(List<Integer> solution) {
		this.solution = solution;
	}
	public Double getpBFitness() {
		return pBFitness;
	}
	public void setpBCost(Double pBFitness) {
		this.pBFitness = pBFitness;
	}

	public UtilityFunction getUtilityFunction() {
		return utilityFunction;
	}

	public void setUtilityFunction(UtilityFunction utilityFunction) {
		this.utilityFunction = utilityFunction;
	}

	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public Double getRealDist() {
		return realDist;
	}

	public void setRealDist(Double realDist) {
		this.realDist = realDist;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	public Integer getQuality() {
		return quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	public Double getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(Double serviceTime) {
		this.serviceTime = serviceTime;
	}

	public Double getFitness() {
		return fitness;
	}

	public void setFitness(Double fitness) {
		this.fitness = fitness;
	}
	public Double getDist() {
		return dist;
	}

	public void setDist(Double dist) {
		this.dist = dist;
	}



	public void setpBFitness(Double pBFitness) {
		this.pBFitness = pBFitness;
	}

	@Override
	public String toString(){
		return "Particle{" +
				"pBFitness=" + pBFitness +
				", quality=" + quality +
				", duration=" + duration +
				", realDist='" + realDist +
				", fitness='" + fitness + '\'' +
				'}';
	}

	public Double getRealDistUtil() {
		return realDistUtil;
	}

	public void setRealDistUtil(Double realDistUtil) {
		this.realDistUtil = realDistUtil;
	}

	public Double getDurationUtil() {
		return durationUtil;
	}

	public void setDurationUtil(Double durationUtil) {
		this.durationUtil = durationUtil;
	}

	public Double getQualityUtil() {
		return qualityUtil;
	}

	public void setQualityUtil(Double qualityUtil) {
		this.qualityUtil = qualityUtil;
	}


	
	
	
}
