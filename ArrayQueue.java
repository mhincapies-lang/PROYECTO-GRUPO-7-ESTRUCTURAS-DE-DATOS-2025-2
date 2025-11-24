public class ArrayQueue<T> {
    private static final int DEFAULT_CAPACITY = 100;
    private T[] data;
    private int front; // El elemento que sale
    private int rear;  // Donde se añade el elemento
    private int size;  // Cantidad de elementos

    @SuppressWarnings("unchecked")
    public ArrayQueue() {
        this.data = (T[]) new Object[DEFAULT_CAPACITY];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }

    @SuppressWarnings("unchecked")
    public ArrayQueue(int capacity) {
        this.data = (T[]) new Object[capacity + 1]; 
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }

    public boolean enqueue(T element) {
        if (isFull()) {
            return false; // Cola llena
        }

        data[rear] = element;
        // Circular: rear + 1 % tamaño
        rear = (rear + 1) % data.length; 
        size++;
        return true;
    }

    public T dequeue() {
        if (isEmpty()) {
            return null; // Cola vacía
        }

        T element = data[front];
        data[front] = null;
        
        // Circular: front + 1 % tamaño
        front = (front + 1) % data.length;
        size--;
        return element;
    }

    public T peek() {
        if (isEmpty()) {
            return null; // Cola vacía
        }
        return data[front];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public boolean isFull() {
        // La cola está llena si el tamaño es igual a la capacidad máxima.
        return size == (data.length - 1); 
    }
}