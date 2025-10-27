package org.example.BasicConcurrency.Synchonize;

/**
 * @Theory:
 * Cơ chế cache coherence (MESI) của CPU đa nhân:<br>
 * Khi Thread 1 (Core 1) ghi biến a, CPU sẽ đánh dấu cache line của nó là Modified gần như ngay lập tức<br>
 * Còn các core khác (Thread 2, Core 2) sẽ bị invalidate “gần như ngay lập tức”,
 * nhưng vẫn có độ trễ rất nhỏ — không phải tức thì tuyệt đối.;
 * @Overview:
 * volatile là từ khóa trong Java giúp đảm bảo rằng <br>
 * khi một thread ghi (write) vào một biến, <br>
 * thì các thread khác luôn đọc được giá trị mới nhất của biến đó từ bộ nhớ chính (RAM).<br>
 * volatile nhẹ hơn, nhanh hơn và chỉ dùng để đảm bảo “visibility”<br>
 * trong khi synchronized đảm bảo cả “visibility” + “atomicity”.
 * @Flow:
 * + Thread 1 (trên Core 1) ghi a = 100 vào cache riêng của nó (L1 hoặc L2) <br>
 * → tạm thời, RAM vẫn chưa đổi. <br>
 * Core 1 đánh dấu dòng cache chứa a là Modified (M) trong MESI protocol. <br>
 * Các core khác (ví dụ core của Thread 2) được thông báo qua bus rằng:
 * “Dòng cache chứa địa chỉ a của mày Invalid rồi nha!”<br>
 * -> Core 2 sẽ đánh dấu bản copy của a trong cache là Invalid.<br>
 * <br>
 * + Trường hợp 1: Không có đồng bộ (volatile, synchronized)<br>
 * Thread 2 vẫn có thể đọc a trong cache của mình, nếu nó chưa nhận được tín hiệu invalidate.<br>
 * → Vậy nó sẽ thấy giá trị cũ (a = 0).<br>
 * Nếu Core 2 đã nhận tín hiệu invalidate, cache của nó đánh dấu a là Invalid<br>
 * → nó phải tải lại từ RAM.<br>
 * -> Nhưng: nếu Core 1 chưa flush dữ liệu từ cache ra RAM,
 * RAM vẫn chứa giá trị cũ (a = 0).<br>
 * Kết quả: Thread 2 vẫn thấy giá trị cũ,
 * mặc dù Thread 1 đã “ghi” rồi (trong cache của nó).<br>
 * <br>
 * + Trường hợp 2: Có đồng bộ (vd: volatile, synchronized)<br>
 * Khi Thread 1 ghi a = 100:<br>
 * → sinh Store Barrier<br>
 * → ép CPU flush giá trị từ cache ra main memory ngay lập tức.<br>
 * Khi Thread 2 đọc a:<br>
 * → sinh Load Barrier<br>
 * → ép CPU đọc lại từ main memory,
 * bỏ qua cache cũ.<br>
 * Kết quả: Thread 2 luôn thấy giá trị mới nhất (100).
 */
public class VolatileExample {
    private static volatile int a = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            a = 100;
            System.out.println("t1 changed a = " + a);
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i<10;i++) {
                try {
                    Thread.sleep(0,1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("t2 read: a = " + a);
            }
        });

        t2.start();
        t1.start();

    }
}

