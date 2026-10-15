public class LinkedList
{
    private Node head;
    private Node tail;
    private int length;


    class Node
    {
        int value;
        Node next;

        Node(int value)
        {
            this.value = value;
        }
    }

    // Constructor: crea la lista con un primer nodo que contiene "value"
    public LinkedList(int value)
    {
        Node newNode = new Node(value);
        this.length = 1;
        this.head = newNode;
        this.tail = newNode;
    }

    // Regresa el primer nodo (o null si la lista está vacía)
    public Node getHead()
    {
        return this.head;
    }

    // Asigna manualmente el nodo cabeza
    public void setHead(Node head)
    {
        this.head = head;
    }

    // Asigna manualmente el nodo cola
    public void setTail(Node tail)
    {
        this.tail = tail;
    }

    // Regresa el último nodo (o null si la lista está vacía).
    // Se corrigió: el original no regresaba nada cuando tail era null y no compilaba.
    public Node getTail()
    {
        return this.tail;
    }

    // Regresa la cantidad de nodos de la lista
    public int getLength()
    {
        return this.length;
    }

    // Append(valor): agrega un nodo al final de la lista.
    // Si la lista está vacía, el nuevo nodo es a la vez head y tail.
    // Si no, se enlaza después de tail y tail pasa a ser el nuevo nodo.
    public void append(int value)
    {
        Node newNode = new Node(value);
        if (length == 0)
        {
            this.head = newNode;
            this.tail = newNode;
        }
        else
        {
            this.tail.next = newNode;
            this.tail = newNode;
        }
        length++;
    }

    // Prepend(valor): agrega un nodo al inicio de la lista.
    // El nuevo nodo apunta al antiguo head y se convierte en el nuevo head.
    // Si la lista estaba vacía, también es la tail.
    public void prepend(int value)
    {
        Node newNode = new Node(value);
        if (length == 0)
        {
            this.head = newNode;
            this.tail = newNode;
        }
        else
        {
            newNode.next = this.head;
            this.head = newNode;
        }
        length++;
    }

    // Insert(indice, valor): agrega un nodo en la posición "index".
    // Regresa false si el índice es inválido (menor que 0 o mayor que length).
    // - index 0      -> equivale a prepend
    // - index length -> equivale a append
    // - otro caso    -> se llega al nodo anterior (index-1) y se enlaza el nuevo nodo en medio
    public boolean insert(int index, int value)
    {
        if (index < 0 || index > length) return false;
        if (index == 0)
        {
            prepend(value);
            return true;
        }
        if (index == length)
        {
            append(value);
            return true;
        }
        Node newNode = new Node(value);
        Node pre = getNode(index - 1);
        newNode.next = pre.next;
        pre.next = newNode;
        length++;
        return true;
    }

    // RemoveFirst(): elimina y regresa el primer nodo (null si la lista está vacía).
    // head avanza al siguiente nodo y el nodo removido se desconecta.
    // Si la lista queda vacía, tail también se pone en null.
    public Node removeFirst()
    {
        if (length == 0) return null;
        Node temp = this.head;
        this.head = this.head.next;
        temp.next = null;
        length--;
        if (length == 0)
        {
            this.tail = null;
        }
        return temp;
    }

    // RemoveLast(): elimina y regresa el último nodo (null si la lista está vacía).
    // Se recorre la lista con dos punteros (temp y pre, el anterior) hasta llegar al final;
    // pre se convierte en la nueva tail y su next se pone en null.
    // Se corrigió: el nodo removido también se desconecta y, si la lista queda vacía, head y tail van a null.
    public Node removeLast()
    {
        if (length == 0) return null;
        Node temp = this.head;
        Node pre = this.head;
        while (temp.next != null)
        {
            pre = temp;
            temp = temp.next;
        }
        this.tail = pre;
        this.tail.next = null;
        length--;
        if (length == 0)
        {
            this.head = null;
            this.tail = null;
        }
        return temp;
    }

    // Remove(indice): elimina y regresa el nodo en la posición "index" (null si el índice es inválido).
    // - index 0        -> equivale a removeFirst
    // - index length-1 -> equivale a removeLast
    // - otro caso      -> el nodo anterior salta al nodo removido, apuntando a su siguiente
    public Node remove(int index)
    {
        if (index < 0 || index >= length) return null;
        if (index == 0) return removeFirst();
        if (index == length - 1) return removeLast();

        Node pre = getNode(index - 1);
        Node temp = pre.next;
        pre.next = temp.next;
        temp.next = null;
        length--;
        return temp;
    }

    // getNode(indice): regresa el nodo en la posición "index" (null si el índice es inválido).
    // Recorre la lista desde head avanzando "index" veces.
    public Node getNode(int index)
    {
        if (index < 0 || index >= length) return null;
        Node temp = this.head;
        for (int i = 0; i < index; i++)
        {
            temp = temp.next;
        }
        return temp;
    }

    // setNode(indice, valor): cambia el valor del nodo en la posición "index".
    // Regresa true si lo logró, false si el índice es inválido.

    public boolean setNode(int index, int value)
    {
        Node temp = getNode(index);
        if (temp == null) return false;
        temp.value = value;
        return true;
    }

    // Medio(): regresa el nodo de en medio usando dos punteros, sin contadores.
    // "slow" avanza de uno en uno y "fast" de dos en dos. Cuando fast llega al final,
    // slow está en la mitad. Si la cantidad de nodos es par, regresa el de la derecha.

    public Node medio()
    {
        if (head == null) return null;
        Node slow = this.head;
        Node fast = this.head;
        while (fast != null && fast.next != null)
        {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // EsCiclo(): regresa true si la lista tiene un ciclo (algoritmo de la tortuga y la liebre de Floyd).
    // "slow" avanza de uno en uno y "fast" de dos en dos. Si hay ciclo, fast eventualmente
    // alcanza a slow; si no, fast llega a null (fin de la lista).
    public boolean esCiclo()
    {
        Node slow = this.head;
        Node fast = this.head;
        while (fast != null && fast.next != null)
        {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }

    // Reversa(): invierte el orden de la lista in-place.
    // Primero se intercambian head y tail. Luego se recorre la lista con tres punteros
    // (before, temp, after) y en cada nodo se invierte la dirección de su "next".
    public void reversa()
    {
        Node temp = this.head;
        this.head = this.tail;
        this.tail = temp;

        Node after = null;
        Node before = null;
        while (temp != null)
        {
            after = temp.next;   // guardamos el siguiente antes de perderlo
            temp.next = before;  // invertimos el enlace
            before = temp;       // avanzamos before
            temp = after;        // avanzamos temp
        }
    }

    // printList(): imprime los valores de la lista separados por espacios.
    // Nota: si la lista tiene un ciclo, este método nunca termina.
    public void printList()
    {
        Node temp = this.head;
        while (temp != null)
        {
            System.out.print(temp.value + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    // Pruebas rápidas de todos los métodos
    public static void main(String[] args)
    {
        LinkedList list = new LinkedList(2);
        list.append(3);
        list.prepend(1);                       // 1 2 3
        list.insert(3, 5);                     // 1 2 3 5
        list.insert(3, 4);                     // 1 2 3 4 5
        list.printList();

        System.out.println("Medio (5 nodos): " + list.medio().value);      // 3
        list.append(6);
        System.out.println("Medio (6 nodos): " + list.medio().value);      // 4

        list.setNode(0, 10);                   // 10 2 3 4 5 6
        System.out.println("getNode(0): " + list.getNode(0).value);        // 10

        list.reversa();                        // 6 5 4 3 2 10
        list.printList();

        list.removeFirst();                    // 5 4 3 2 10
        list.removeLast();                     // 5 4 3 2
        list.remove(1);                        // 5 3 2
        list.printList();

        System.out.println("¿Ciclo? " + list.esCiclo());                   // false
        list.getTail().next = list.getHead();  // creamos un ciclo
        System.out.println("¿Ciclo? " + list.esCiclo());                   // true
    }
}