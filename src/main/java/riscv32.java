import java.util.Arrays;

public class riscv32 {
    public static void main(String[] args) {
        Emulator emulator = new Emulator(1024 * 4);

        int base = 0x100; // 配列を置くメモリのベースアドレス

        int[] program = {
                Assembler.LUI(10, base >> 12),
                Assembler.ADDI(10, 10, base & 0xFFF),

                Assembler.LUI(11, 0),
                Assembler.ADDI(11, 11, 7),
                Assembler.SW(10, 11, 0), // mem[x10 + 0] = 7
                Assembler.LUI(11, 0),
                Assembler.ADDI(11, 11, 1),
                Assembler.SW(10, 11, 4),
                Assembler.LUI(11, 0),
                Assembler.ADDI(11, 11, 5),
                Assembler.SW(10, 11, 8),

                Assembler.LUI(5, 0),
                Assembler.ADDI(5, 5, 0),

                Assembler.LUI(6, 0),
                Assembler.ADDI(6, 6, 0),

                Assembler.LUI(7, 0),
                Assembler.ADDI(7, 7, 3),

                Assembler.SLLI(8, 6, 2),        // x8 = index * 4
                Assembler.ADD(9, 10, 8),        // x9 = base + offset
                Assembler.LW(11, 9, 0),         // x11 = mem[x9]

                Assembler.BGE(5, 11, 8),       // if x5 >= x11 → skip
                Assembler.ADD(5, 11, 0),       // x5 = x11

                Assembler.ADDI(6, 6, 1),       // index++
                Assembler.BLT(6, 7, -24),      // if index < 3 → loop （6命令分戻る = -24）
        };

        Arrays.stream(program).forEach(
                c -> System.out.printf("0x%x\n", c)
        );
        emulator.setProgram(program);
        emulator.run();
    }
}
