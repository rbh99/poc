package com.example.core.oo;

import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.function.Function;
import java.util.regex.*;

import java.util.ArrayList;
import java.util.Scanner;


public class VisitorTest {



    enum Color {
        RED, GREEN
    }

    abstract class Tree {

        private int value;
        private Color color;
        private int depth;

        public Tree(int value, Color color, int depth) {
            this.value = value;
            this.color = color;
            this.depth = depth;
        }

        public int getValue() {
            return value;
        }

        public Color getColor() {
            return color;
        }

        public int getDepth() {
            return depth;
        }

        public abstract void accept(TreeVis visitor);
    }

    @ToString
    class TreeNode extends Tree {

        private ArrayList<Tree> children = new ArrayList<>();

        public TreeNode(int value, Color color, int depth) {
            super(value, color, depth);
        }

        public void accept(TreeVis visitor) {
            visitor.visitNode(this);

            for (Tree child : children) {
                child.accept(visitor);
            }
        }

        public void addChild(Tree child) {
            children.add(child);
        }
    }

    @ToString
    class TreeLeaf extends Tree {

        public TreeLeaf(int value, Color color, int depth) {
            super(value, color, depth);
        }

        public void accept(TreeVis visitor) {
            visitor.visitLeaf(this);
        }
    }


    static abstract class TreeVis
    {
        public abstract int getResult();
        public abstract void visitNode(TreeNode node);
        public abstract void visitLeaf(TreeLeaf leaf);

    }

    static class SumInLeavesVisitor extends TreeVis {
        int sum = 0;
        public int getResult() {
            return sum;
        }

        public void visitNode(TreeNode node) {
            System.out.println("doing nothing for visiting node "+node);
        }

        public void visitLeaf(TreeLeaf leaf) {
            System.out.println("adding sum for " + leaf);
            sum += leaf.getValue();
        }
    }

    static class ProductOfRedNodesVisitor extends TreeVis {
        int prod = 1;
        public int getResult() {
            return prod;
        }

        public void visitNode(TreeNode node) {
            if (node.getColor() == Color.RED)
                prod *= node.getValue();
        }

        public void visitLeaf(TreeLeaf leaf) {
            if (leaf.getColor() == Color.RED)
                prod *= leaf.getValue();
        }
    }

    static class FancyVisitor extends TreeVis {
        int nonLeafSum = 0;
        int greenLeafSum = 0;

        public int getResult() {
            return Math.abs(nonLeafSum - greenLeafSum);
        }

        public void visitNode(TreeNode node) {
            if (node.getDepth() % 2 == 0){
                nonLeafSum += node.getValue();
            }
        }

        public void visitLeaf(TreeLeaf leaf) {
            if (leaf.getColor() == Color.GREEN)
                greenLeafSum += leaf.getValue();
        }
    }



        private  Tree solve() {
            //read the tree from STDIN and return its root as a return value of this function
            String initialString = "5\n" +
                    "4 7 2 5 12\n" +
                    "0 1 0 0 1\n" +
                    "1 2\n" +
                    "1 3\n" +
                    "3 4\n" +
                    "3 5\n";
            InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());


            Scanner in = new Scanner(targetStream);//new Scanner(System.in);
            int n = Integer.parseInt(in.nextLine());
            String line = in.nextLine();
            System.out.println("read line"+line);

            int[] values = Arrays.stream(line.split(" ")).mapToInt(Integer::valueOf).toArray();
            System.out.println("values = "+ Arrays.toString(values));
            Color[] colors = Arrays.stream(in.nextLine().split(" "))
                    .map(c->Objects.equals(c, "0") ? Color.RED : Color.GREEN)
                    .toArray(Color[]::new);
            System.out.println("Colors = "+ Arrays.toString(colors));
            Map<Integer, List<Integer>> indexToChildIndexMap = new LinkedHashMap<>();
            Map<Integer, Integer> indexToParentMap = new HashMap<>();
            Tree[] tree = new Tree[n];

            while (in.hasNext()) {
                String s = in.nextLine();
                int v1 = Character.getNumericValue(s.charAt(0)) -1;
                int v2 = Character.getNumericValue(s.charAt(2)) -1;
                indexToChildIndexMap.computeIfAbsent(v1, k -> new ArrayList<>());
                indexToChildIndexMap.get(v1).add(v2);
                indexToParentMap.put(v2,v1);
            }

            //add root
            TreeNode root = new TreeNode(values[0],colors[0], 0);
            tree[0] = root;

            for (Map.Entry<Integer, Integer> e: indexToParentMap.entrySet()){
                TreeNode parent = (TreeNode)tree[e.getValue()];
                var childIndexList = indexToChildIndexMap.get(e.getKey());
                boolean isLeaf = childIndexList == null || childIndexList.isEmpty();
                if (isLeaf){
                    Tree leaf = new TreeLeaf(values[e.getKey()],colors[e.getKey()], parent.getDepth()+1);
                    parent.addChild(leaf);
                    tree[e.getKey()] = leaf;
                }else{
                    Tree node = new TreeNode(values[e.getKey()],colors[e.getKey()], parent.getDepth()+1);
                    parent.addChild(node);
                    tree[e.getKey()] = node;
                }
            }
            System.out.println("tree is: "+tree[0]);
            return tree[0];
        }



        public static void main(String[] args) {
        VisitorTest test =  new VisitorTest();
            Tree root = test.solve();
            SumInLeavesVisitor vis1 = new SumInLeavesVisitor();
            ProductOfRedNodesVisitor vis2 = new ProductOfRedNodesVisitor();
            FancyVisitor vis3 = new FancyVisitor();

            root.accept(vis1);
            root.accept(vis2);
            root.accept(vis3);

            int res1 = vis1.getResult();
            int res2 = vis2.getResult();
            int res3 = vis3.getResult();

            System.out.println(res1);
            System.out.println(res2);
            System.out.println(res3);
        }
    }


