package ch.zhaw.ads;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class TreeTraversal<T extends Comparable<T>> implements Traversal<T> {
    private final TreeNode<T> root;
    private String min;
    private String max;

    public TreeTraversal(TreeNode<T> root) {
        this.root = root;
    }

    public void inorder(Visitor<T> vis) {
        inorder(root, vis);
    }

    private void inorder(TreeNode<T> node, Visitor<T> visitor){
        if(node != null) {
            inorder(node.left, visitor);
            visitor.visit(node.getValue());
            inorder(node.right, visitor);
        }
    }

    public void preorder(Visitor<T> vis) {
        preorder(root, vis);
    }

    private void preorder(TreeNode<T> node, Visitor<T> visitor){
        if(node != null) {
            visitor.visit(node.getValue());
            preorder(node.left, visitor);
            preorder(node.right, visitor);
        }
    }

    public void postorder(Visitor<T> vis) {
        postorder(root, vis);
    }

    private void postorder(TreeNode<T> node, Visitor<T> visitor){
        if(node != null) {
            postorder(node.left, visitor);
            postorder(node.right, visitor);
            visitor.visit(node.getValue());
        }
    }

    @Override
    public void levelorder(Visitor<T> vistor) {
        levelorder(root, vistor);
    }

    private void levelorder(TreeNode<T> node, Visitor<T> visitor){
        Queue<TreeNode<T>> queue = new LinkedBlockingQueue<>();
        if(node != null) {
            queue.add(node);
        }

        while(!queue.isEmpty()) {
            node = queue.remove();
            visitor.visit(node.getValue());
            if(node.left != null){
                queue.add(node.left);
            }

            if(node.right != null) {
                queue.add(node.right);
            }
        }
    }

    @Override
    public void interval(T min, T max, Visitor<T> visitor) {
        this.min = (String) min;
        this.max = (String) max;
        getNextNode(root, visitor);
    }

    public void getNextNode(TreeNode<T> node, Visitor<T> visitor) {
        if(node.getValue().toString().compareTo(max) <= 0 && node.getValue().toString().compareTo(min) >= 0) {
            visitor.visit(node.getValue());
        }

        if (node.left != null) {
            if(node.getValue().toString().compareTo(min) >= 0){
                getNextNode(node.left, visitor);
            }
        }

        if (node.right != null) {
            if(node.getValue().toString().compareTo(max) <= 0){
                getNextNode(node.right, visitor);
            }
        }
    }
}
