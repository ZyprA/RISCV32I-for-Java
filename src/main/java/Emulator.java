public class Emulator {
    private final Memory memory;
    private final CPU cpu;
    private int pc = 0;
    private int endAddress;

    public Emulator(Memory memory, CPU cpu) {
        this.memory = memory;
        this.cpu = cpu;
    }

    public void loadProgram(int[] instructions, int baseAddress) {
        for (int i = 0; i < instructions.length; i++) {
            memory.storeWord(baseAddress + i * 4, instructions[i]);
        }
        pc = baseAddress;
        endAddress = baseAddress + instructions.length * 4;;
    }

    public void run() {
        while (true) {
            int instrWord = memory.loadWord(pc);
            Instruction instr = Instruction.decode(instrWord);
            System.out.printf("Executing at PC=0x%08X: %s\n", pc, instr);
            if (instr != null) cpu.execute(instr);

            pc += 4;
            if (instr != null && instr.op == null || pc >= endAddress) {
                System.out.println("HALT encountered. Stopping execution.");
                break;
            }
        }
    }

    public int getPC() {
        return pc;
    }
}