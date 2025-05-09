public class riscv32 {
    public static void main(String[] args) {
        Emulator emulator = new Emulator(1024);

        int[] program = {
                0x00A00093,  // ADDI x1, x0, 10
                0x001080B3   // ADD  x1, x1, x1 → x2 = x1 + x1
        };
        emulator.setProgram(program);
        emulator.run();
    }
}
