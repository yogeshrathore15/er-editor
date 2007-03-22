/*
 * Created on 28.10.2006
 */
package ru.amse.soultakov.structures.cartesiantree;


public class CartesianTree<T extends Comparable<T>> {
    
    class TreeNode {
        private T value;
        private TreeNode left;
        private TreeNode right;
        private TreeNode parent;
        private double priority;
        
        public TreeNode(T value, TreeNode left, TreeNode right, 
                TreeNode parent, double priority) {
            super();
            this.value = value;
            this.left = left;
            this.right = right;
            this.parent = parent;
            this.priority = priority;
        }
        
        public TreeNode(T value, TreeNode left, TreeNode right, 
                TreeNode parent) {
            this(value, left, right, parent, getRandomizedPriority());
        }
        
        private void rotateLeft() {
            TreeNode x = this;
            TreeNode y = x.getRight();
            TreeNode p = x.getParent();
            x.setRight(y.getLeft());
            if (x.getRight() != null) {
              x.getRight().setParent(x);
            }
            if (p.getLeft() == x) {
              p.setLeft(y);
            }
            else {
              p.setRight(y);
            }
            y.setParent(p);
            y.setLeft(x);
            x.setParent(y);
        }
        
        private void rotateRight() {
            TreeNode y = this;
            TreeNode x = y.getLeft();
            TreeNode p = y.getParent();
            y.setLeft(x.getRight());
            if (y.getLeft() != null) {
              y.getLeft().setParent(y);
            }
            if (p.getRight() == y) {
              p.setRight(x);
            } else {
              p.setLeft(x);
            }
            x.setParent(p);
            x.setRight(y);
            y.setParent(x);
        }
        
        private void siftUp() {
            TreeNode p = getParent();
            if (getPriority() < p.getPriority()) {
                if (p.getLeft() == this) {
                    p.rotateRight();
                } else {
                    p.rotateLeft();
                }
                siftUp();
            }
        }
        
        private void siftDown() {
            double lcPriority = getLeft() != null ? getLeft().getPriority() 
                        : Double.POSITIVE_INFINITY;
            double rcPriority = getRight() != null ? getRight().getPriority() 
                        : Double.POSITIVE_INFINITY;
            double minPriority = (lcPriority < rcPriority) ? lcPriority : rcPriority;
            if (getPriority() <= minPriority) {
                return;
            }
            if (lcPriority < rcPriority) {
                rotateRight();
            } else {
                rotateLeft();
            }
            siftDown();
        }

        public TreeNode getLeft() {
            return left;
        }

        public void setLeft(TreeNode left) {
            this.left = left;
        }

        public TreeNode getParent() {
            return parent;
        }

        public void setParent(TreeNode parent) {
            this.parent = parent;
        }

        public TreeNode getRight() {
            return right;
        }

        public void setRight(TreeNode right) {
            this.right = right;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
        
        public double getPriority() {
            return priority;
        }
        
        public void setPriority(double priority) {
            this.priority = priority;
        }
        
        public boolean isLeft() {
            return parent.left == this;
        }
        
        public boolean isRight() {
            return parent.right == this;
        }
        
        public String toString() {
            return value.toString();
        }
    }
    
    private TreeNode root; //фиктивный узел
       
    public CartesianTree() {
        root = new TreeNode(null, null, null, null, -1.0);
    }
    
    private static double getRandomizedPriority() {            
        return Math.random();
    }
    
    public void insert(T element) {
        int result = 1;
        TreeNode p = root;
        TreeNode n = root.getRight();
        while (n != null) {
            p = n;
            result = element.compareTo(p.getValue());
            if (result < 0) {
                n = p.getLeft();
            } else {
                n = p.getRight();
            }
        }
        TreeNode win = new TreeNode(element, null, null, p);
        if (result < 0) {
            p.setLeft(win);
        } else {
            p.setRight(win);
        }
        win.siftUp();
    }
    
    public boolean exists(T element) {
        return findElement(element) != null;
    }
    
    public boolean remove(T element) {
        TreeNode node = findElement(element);
        if (node == null) {
            return false;
        }
        node.setPriority(1.5);
        node.siftDown();
        TreeNode p = node.getParent();
        if (p.getLeft() == node) {
            p.setLeft(null);
        } else {
            p.setRight(null);
        }
        return true;
    }
    
    private TreeNode findElement(T element) {
        TreeNode p;
        TreeNode n = root.getRight();
        while (n != null) {
            p = n;
            int result = element.compareTo(p.getValue());
            if (result < 0) {
                n = p.getLeft();
            } else if (result > 0) {
                n = p.getRight();
            } else {
                return p; 
            }
        }
        return null;
    }
    
    public T next(T element) {
        TreeNode p = root;
        TreeNode n = root.getRight();
        if (n == null) {
            return null;
        }
        while (n != null) {
            p = n;
            int result = element.compareTo(p.getValue());
            if (result < 0) {
                n = p.getLeft();
            } else {
                n = p.getRight();
            }
        }
        if (element.compareTo(p.getValue()) < 0) {
            return p.getValue();
        } else {
            for ( ; p.isRight(); p = p.getParent()) {
                if (p == root.getRight()) {
                    return null;
                }
            }
            return p.getParent().getValue();
        }
    }
       
    public T previous(T element) {
        TreeNode p = root;
        TreeNode n = root.getRight();
        if (n == null) {
            return null;
        }
        while (n != null) {
            p = n;
            int result = element.compareTo(p.getValue());
            if (result <= 0) {
                n = p.getLeft();
            } else {
                n = p.getRight();
            }
        }
        if (element.compareTo(p.getValue()) > 0) {
            return p.getValue();
        } else {
            for ( ; p.isLeft(); p = p.getParent()) {
                if (p == root.getRight()) {
                    return null;
                }
            }
            return p.getParent().getValue();
        }
    }
}
