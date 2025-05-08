public class riscv32 {
    public static void main(String[] args) {
        Memory memory = new Memory(1024);
        CPU cpu = new CPU(memory);
        Emulator emulator = new Emulator(memory, cpu);

        int[] program = {
                0x00A00093,  // ADDI x1, x0, 10
                0x001080B3   // ADD  x1, x1, x1 → x2 = x1 + x1
        };

        emulator.loadProgram(program, 0);
        emulator.run();

        cpu.printRegisters(); // 実行結果確認用
    }
}
