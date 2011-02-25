package droid.ipm.util;

public class Pair implements Comparable<Pair>{
	 
	  public String fst;
	  public Double snd;
	 
	  public Pair(String fst, Double snd) {
	    this.fst = fst;
	    this.snd = snd;
	  }
	 
	  public String getFirst() { return fst; }
	  public Double getSecond() { return snd; }
	 
	  public void setFirst(String v) { fst = v; }
	  public void setSecond(Double v) { snd = v; }
	 
	  public String toString() {
	    return "Pair[" + fst + "," + snd + "]";
	  }
	 
	  private static boolean equals(Object x, Object y) {
	    return (x == null && y == null) || (x != null && x.equals(y));
	  }
	 
	  public boolean equals(Object other) {
	     return
	      other instanceof Pair &&
	      equals(fst, ((Pair)other).fst) &&
	      equals(snd, ((Pair)other).snd);
	  }
	  
	  public int compareTo(Pair compareObject){
		  if (getSecond() < compareObject.getSecond())
			  return -1;
	      else if (getSecond() == compareObject.getSecond())
	    	  return 0;
	      else
	    	  return 1;
	  }
	  
}