package Student_management_app;

class StudentManagement {
    private Stack<Student> students;

    public StudentManagement() {
        this.students = new Stack<>();
    }

    // Thêm sinh viên mới
    public void addStudent(Student student) {
        if (searchStudent(student.getId()) != null) {
            throw new DuplicateStudentException("Student ID already exists");
        }
        students.push(student);
    }

    // Cập nhật thông tin sinh viên
    public void updateStudent(int id, String newName, double newScore) {
        Stack<Student> tempStack = new Stack<>();
        boolean found = false;

        while (!students.isEmpty()) {
            Student student = students.pop();
            if (student.getId() == id) {
                tempStack.push(new Student(id, newName, newScore));
                found = true;
            } else {
                tempStack.push(student);
            }
        }

        while (!tempStack.isEmpty()) {
            students.push(tempStack.pop());
        }

        if (!found) {
            System.out.println("Student with ID " + id + " not found.");
        }
    }

    // Xóa sinh viên theo ID
    public void deleteStudent(int id) {
        Stack<Student> tempStack = new Stack<>();

        while (!students.isEmpty()) {
            Student student = students.pop();
            if (student.getId() != id) {
                tempStack.push(student);
            }
        }

        while (!tempStack.isEmpty()) {
            students.push(tempStack.pop());
        }
    }

    // Tìm sinh viên theo ID
    public Student searchStudent(int id) {
        // Create a temporary stack to preserve the original order
        Stack<Student> tempStack = new Stack<>();
        Student foundStudent = null;

        while (!students.isEmpty()) {
            Student student = students.pop();
            if (student.getId() == id) {
                foundStudent = student;
            }
            tempStack.push(student);
        }

        // Restore the original stack
        while (!tempStack.isEmpty()) {
            students.push(tempStack.pop());
        }

        return foundStudent;
    }
    // Hiển thị tất cả sinh viên
    public void displayStudents() {
        Stack<Student> tempStack = new Stack<>();

        while (!students.isEmpty()) {
            Student student = students.pop();
            System.out.println(student);
            tempStack.push(student);
        }

        while (!tempStack.isEmpty()) {
            students.push(tempStack.pop());
        }
    }

    // Custom Stack class to replace Java's built-in Stack
    private class Stack<T> {
        private Node<T> top;
        private int size;

        private class Node<T> {
            T data;
            Node<T> next;

            Node(T data) {
                this.data = data;
                this.next = null;
            }
        }

        public void push(T item) {
            Node<T> newNode = new Node<>(item);
            newNode.next = top;
            top = newNode;
            size++;
        }

        public T pop() {
            if (isEmpty()) {
                throw new RuntimeException("Stack is empty");
            }
            T item = top.data;
            top = top.next;
            size--;
            return item;
        }

        public boolean isEmpty() {
            return top == null;
        }

        public int size() {
            return size;
        }

        public T peek() {
            if (isEmpty()) {
                throw new RuntimeException("Stack is empty");
            }
            return top.data;
        }

        public void clear() {
            top = null;
            size = 0;
        }
    }

    // Custom Quick Sort implementation using Stack
    public void sortStudents() {
        if (students.isEmpty()) return;

        // Convert stack to array for sorting
        Student[] studentArray = new Student[students.size()];
        int index = 0;
        Stack<Student> tempStack = new Stack<>();

        while (!students.isEmpty()) {
            Student student = students.pop();
            studentArray[index++] = student;
            tempStack.push(student);
        }

        // Restore original stack
        while (!tempStack.isEmpty()) {
            students.push(tempStack.pop());
        }

        // Quick Sort implementation
        quickSort(studentArray, 0, studentArray.length - 1);

        // Push sorted students back to stack
        for (int i = 0; i < studentArray.length; i++) {
            students.push(studentArray[i]);
        }
    }

    private void quickSort(Student[] arr, int low, int high) {
        Stack<int[]> sortStack = new Stack<>();
        sortStack.push(new int[]{low, high});

        while (!sortStack.isEmpty()) {
            int[] bounds = sortStack.pop();
            int start = bounds[0];
            int end = bounds[1];

            if (start >= end) continue;

            int pivotIndex = partition(arr, start, end);

            if (pivotIndex - 1 > start) {
                sortStack.push(new int[]{start, pivotIndex - 1});
            }

            if (pivotIndex + 1 < end) {
                sortStack.push(new int[]{pivotIndex + 1, end});
            }
        }
    }

    private int partition(Student[] arr, int low, int high) {
        Student pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j].getScore() < pivot.getScore()) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    private void swap(Student[] arr, int i, int j) {
        Student temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}