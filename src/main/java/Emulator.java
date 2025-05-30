public class Emulator {
    private final CPU cpu;

    public Emulator(int memorysize) {
        Memory memory = new Memory(memorysize);
        this.cpu = new CPU(memory);
    }

    public void run() {
        boolean stop = false;
        while (!stop) {
            stop = cpu.execute();
        }
        cpu.printRegisters();
    }

    public void setProgram(int[] instructions) {
        cpu.loadProgram(instructions, 0);
    }
}