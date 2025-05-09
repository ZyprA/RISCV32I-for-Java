public class riscv32 {
    public static void main(String[] args) {
        Emulator emulator = new Emulator(1024);

        int[] program = {
                Assembler.LUI(1, 0),             // x1 = 0
                Assembler.LUI(2, 0),             // x2 = 0（カウンタ）
                Assembler.LUI(3, 0),             // x3 = 0（上限用）
                Assembler.ADDI(3, 3, 5),         // x3 = 5
                Assembler.ADDI(1, 1, 1),         // x1 += 1（何か処理）
                Assembler.ADDI(2, 2, 1),         // x2 += 1（カウンタ+1）
                Assembler.BNE(2, 3, -8)
        };
        emulator.setProgram(program);
        emulator.run();
    }
}
