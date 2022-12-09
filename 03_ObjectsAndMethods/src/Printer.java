public class Printer {
    public String queue = "";
    public int pendingPagesCount = 0;
    public int allPrintedPages = 0;

    public void appendDoc(String text) { appendDoc(text, "no name", 1); }

    public void appendDoc(String text, String name) { appendDoc(text, name, 1); }

    public void appendDoc(String text, String name, int count) {
        queue = queue + "\n" + text + " - " + name + " - " + count + " стр.";
        pendingPagesCount = pendingPagesCount + count;
    }
    public int getPendingPagesCount () {
        return pendingPagesCount;
    }
    public void clearQueue () {
        queue = "";
    }

    public void print() {
        System.out.println();
        if (queue.isEmpty()) {
            System.out.println("В очереди нет документов");
        } else {
            System.out.println(queue);
            clearQueue();
            System.out.println("В очереди нет документов");
        }
        allPrintedPages = allPrintedPages + pendingPagesCount;

    }
    public int getAllPrintedPages() {
        return allPrintedPages;
    }
}
