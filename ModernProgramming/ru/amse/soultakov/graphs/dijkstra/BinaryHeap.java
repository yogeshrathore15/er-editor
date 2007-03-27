package ru.amse.soultakov.graphs.dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Soultakov Maxim
 */
class BinaryHeap {

    private ArrayList<DistanceVertex> heap = new ArrayList<DistanceVertex>();

    private Map<DistanceVertex, Integer> heapMap = new HashMap<DistanceVertex, Integer>();

    public BinaryHeap() {
        heap.add(new DistanceVertex(null, 0));
    }

    public void add(DistanceVertex comp) {
        int hs;
        DistanceVertex hd;
        hs = heap.size();
        heapMap.put(comp, heap.size());
        heap.add(comp);
        while ((hs / 2 > 0) && (heap.get(hs).compareTo(heap.get(hs / 2)) > 0)) {
            hd = heap.get(hs);
            heapMap.put(heap.get(hs / 2), hs);
            heapMap.put(hd, hs / 2);
            heap.set(hs, heap.get(hs / 2));
            heap.set(hs / 2, hd);
            hs /= 2;
        }
    }

    public DistanceVertex min() {
        DistanceVertex hm = heap.get(1);
        heapMap.remove(heap.get(1));
        heapMap.put(heap.get(heap.size() - 1), 1);
        heap.set(1, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);
        if (heap.size() > 1)
            sift(1);
        return hm;
    }

    private void sift(int number) {
        DistanceVertex hd;
        if ((number * 2 > heap.size() - 1) & (number * 2 > heap.size() - 2)) {
        } else if ((number * 2 <= heap.size() - 1) & (number * 2 > heap.size() - 2)) {
            if ((heap.get(number).compareTo(heap.get(number * 2)) < 0)) {
                hd = heap.get(number);

                heapMap.put(heap.get(number * 2), number);
                heapMap.put(hd, number * 2);

                heap.set(number, heap.get(number * 2));
                heap.set(number * 2, hd);
            }
        } else {
            if ((heap.get(number).compareTo(heap.get(number * 2 + 1)) < 0)
                    && (heap.get(number * 2).compareTo(heap.get(number * 2 + 1)) <= 0)) {
                hd = heap.get(number);
                heapMap.put(heap.get(number * 2 + 1), number);
                heapMap.put(hd, number * 2 + 1);
                heap.set(number, heap.get(number * 2 + 1));
                heap.set(number * 2 + 1, hd);
                sift(number * 2 + 1);
            } else {
                if ((heap.get(number).compareTo(heap.get(number * 2)) < 0)
                        && (heap.get(number * 2 + 1).compareTo(heap.get(number * 2)) <= 0)) {
                    hd = heap.get(number);
                    heapMap.put(heap.get(number * 2), number);
                    heapMap.put(hd, number * 2);
                    heap.set(number, heap.get(number * 2));
                    heap.set(number * 2, hd);
                    sift(number * 2);
                }
            }
        }
    }

    public void decreaseKey(DistanceVertex comp) {
        int hs = heapMap.get(comp);
        while ((hs / 2 > 0) && ((heap.get(hs).compareTo(heap.get(hs / 2)) > 0))) {
            DistanceVertex hd = heap.get(hs);
            heapMap.put(heap.get(hs / 2), hs);
            heapMap.put(hd, hs / 2);
            heap.set(hs, heap.get(hs / 2));
            heap.set(hs / 2, hd);
            hs = hs / 2;
        }
    }

    public int size() {
        return heap.size() - 1;
    }

}

class DistanceVertex implements Comparable<DistanceVertex> {
    private Vertex vertex;

    private int distance;

    DistanceVertex(Vertex v,int value) {
        vertex = v;
        distance = value;
    }

    public Vertex getVertex() {
        return vertex;

    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int d) {
        distance = d;
    }

    public int compareTo(DistanceVertex o) {
        if (o.distance > distance) {
            return 1;
        }
        if (o.distance < distance) {
            return -1;
        }
        return 0;
    }
}
