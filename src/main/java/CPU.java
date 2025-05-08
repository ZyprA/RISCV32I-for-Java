public class CPU {
    private final int[] registers = new int[32];
    private final Memory memory;

    public CPU(Memory memory) {
        this.memory = memory;
    }

    public void execute(Instruction instr) {
        int rs1 = registers[instr.rs1];
        int rs2 = registers[instr.rs2];
        int imm = instr.imm;
        int result;

        switch (instr.op) {
            // R-type
            case ADD: result = rs1 + rs2; break;
            case SUB: result = rs1 - rs2; break;
            case SLL: result = rs1 << (rs2 & 0x1F); break;
            case SLT: result = (rs1 < rs2) ? 1 : 0; break;
            case SLTU: result = (Integer.compareUnsigned(rs1, rs2) < 0) ? 1 : 0; break;
            case XOR: result = rs1 ^ rs2; break;
            case SRL: result = rs1 >>> (rs2 & 0x1F); break;
            case SRA: result = rs1 >> (rs2 & 0x1F); break;
            case OR: result = rs1 | rs2; break;
            case AND: result = rs1 & rs2; break;

            // I-type
            case ADDI: result = rs1 + imm; break;
            case SLTI: result = (rs1 < imm) ? 1 : 0; break;
            case SLTIU: result = (Integer.compareUnsigned(rs1, imm) < 0) ? 1 : 0; break;
            case XORI: result = rs1 ^ imm; break;
            case ORI: result = rs1 | imm; break;
            case ANDI: result = rs1 & imm; break;
            case SLLI: result = rs1 << (imm & 0x1F); break;
            case SRLI: result = rs1 >>> (imm & 0x1F); break;
            case SRAI: result = rs1 >> (imm & 0x1F); break;

            // Load
            case LB: result = memory.loadByte(rs1 + imm); break;
            case LH: result = memory.loadHalf(rs1 + imm); break;
            case LW: result = memory.loadWord(rs1 + imm); break;
            case LBU: result = memory.loadByte(rs1 + imm) & 0xFF; break;
            case LHU: result = memory.loadHalf(rs1 + imm) & 0xFFFF; break;

            // Store
            case SB: memory.storeByte(rs1 + imm, (byte)(rs2 & 0xFF)); return;
            case SH: memory.storeHalf(rs1 + imm, (short)(rs2 & 0xFFFF)); return;
            case SW: memory.storeWord(rs1 + imm, rs2); return;

            // Branch
            case BEQ: if (rs1 == rs2) addPCOffset(imm); return;
            case BNE: if (rs1 != rs2) addPCOffset(imm); return;
            case BLT: if (rs1 < rs2) addPCOffset(imm); return;
            case BGE: if (rs1 >= rs2) addPCOffset(imm); return;
            case BLTU: if (Integer.compareUnsigned(rs1, rs2) < 0) addPCOffset(imm); return;
            case BGEU: if (Integer.compareUnsigned(rs1, rs2) >= 0) addPCOffset(imm); return;

            // Jump
            case JAL: setRegister(instr.rd, getPC() + 4); addPCOffset(imm); return;
            case JALR: setRegister(instr.rd, getPC() + 4); setPC((rs1 + imm) & ~1); return;

            // U-type
            case LUI: result = imm; break;
            case AUIPC: result = getPC() + imm; break;

            default:
                throw new IllegalArgumentException("Unknown instruction: " + instr.op);
        }

        setRegister(instr.rd, result);
    }

    private int pc = 0;

    public int getPC() {
        return pc;
    }

    public void setPC(int newPC) {
        pc = newPC;
    }

    public void addPCOffset(int offset) {
        pc += offset;
    }

    public void stepPC() {
        pc += 4;
    }

    public void setRegister(int index, int value) {
        if (index != 0) registers[index] = value;
    }

    public int getRegister(int index) {
        return registers[index];
    }

    public void printRegisters() {
        for (int i = 0; i < 32; i++) {
            System.out.printf("x%d = %d\n", i, registers[i]);
        }
    }
}