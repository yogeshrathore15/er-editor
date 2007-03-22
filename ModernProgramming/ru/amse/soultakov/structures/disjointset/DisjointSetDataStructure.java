/*
 * Created on 30.11.2006
 */
package ru.amse.soultakov.structures.disjointset;

import java.util.HashMap;
import java.util.Map;

public class DisjointSetDataStructure { 
    
    private static class Element {
        
        private int index;
        private int rank;
        private Element parent;
        
        public Element(int value, int rank) {
            this.index = value;
            this.rank = rank;
        }
        
        /**
         * @return the rank
         */
        public int getRank() {
            return rank;
        }
        
        /**
         * @param rank the rank to set
         */
        public void setRank(int rank) {
            this.rank = rank;
        }
        
        /**
         * @return the index
         */
        public int getIndex() {
            return index;
        }
        
        /**
         * @param index the index to set
         */
        public void setIndex(int value) {
            this.index = value;
        }

        /**
         * @param parent the parent to set
         */
        public void setParent(Element parent) {
            this.parent = parent;
        }

        /**
         * @return the parent
         */
        public Element getParent() {
            return parent;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int PRIME = 31;
            int result = 1;
            result = PRIME * result + rank;
            result = PRIME * result + index;
            return result;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final Element other = (Element) obj;
            if (rank != other.rank)
                return false;
            if (index != other.index)
                return false;
            return true;
        }
        
        @Override
        public String toString() {
            return "[Index=" + index + " Parent=" + (parent == this ? "this" : parent) + "]";
        }
        
    }
    
    private Element[] elements;
    private int[] minimums;
    
    public DisjointSetDataStructure(int elementsCount) {
        elements = new Element[elementsCount + 1];
        minimums = new int[elementsCount + 1];
        for(int i = 1; i <= elementsCount; i++) {
            elements[i] = new Element(i,0);
            elements[i].setParent(elements[i]);
            minimums[i] = i;
        }
    }
    
    public void union(int index1, int index2) {
        link(findSet(index1), findSet(index2));
        for(int element : minimums) {
            System.out.print(element + " ");
        }
        System.out.println();
    }
    
    private void link(Element x, Element y) {
        System.out.println(x);
        System.out.println(y);
        if (x.getRank() > y.getRank()) {
            x.setParent(y);
        } else {
            y.setParent(x);
        }
        if (x.getRank() == y.getRank()) {
            y.setRank(y.getRank() + 1);
        }
        int min = Math.min(minimums[x.getIndex()], minimums[y.getIndex()]);
        minimums[y.getIndex()] = min;
        minimums[x.getIndex()] = min;
    }
    
    public Element findSet(int index) {
        if (elements[index].getParent() != elements[index]) {
            Element parent = elements[index].getParent();
            elements[index].setParent(findSet(parent.getIndex()));
        }
        return elements[index].getParent();
    }
    
    public int getMimimum(int index) {
        return minimums[findSet(index).getIndex()];
    }
    
}
