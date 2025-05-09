public class CPU {
    private final int[] registers = new int[32];
    private final Memory memory;
    private int pc = 0;

    public CPU(Memory memory) {
        this.memory = memory;
    }

    public boolean execute() {
        Instruction instr = fetchAndDecode();
        if (instr == null) return true;

        int rs1 = registers[instr.rs1];
        int rs2 = registers[instr.rs2];
        int imm = instr.imm;
        int result = 0;

        // debug
        System.out.printf("Executing at PC=0x%08X: %s | ", pc, instr);
        System.out.printf("[x[%d]]=%d x[%d]=%d\n",instr.rs1,rs1,instr.rs2,rs2);

        switch (instr.op) {
            case ADD: result = rs1 + rs2; break;
            case SUB: result = rs1 - rs2; break;
            case SLT: result = (rs1 < rs2) ? 1 : 0; break;
            case SLTU: result = (Integer.compareUnsigned(rs1, rs2) < 0) ? 1 : 0; break;
            case AND: result = rs1 & rs2; break;
            case OR: result = rs1 | rs2; break;
            case XOR: result = rs1 ^ rs2; break;
            case SLL: result = rs1 << (rs2 & 0x1F); break;
            case SRL: result = rs1 >>> (rs2 & 0x1F); break;
            case SRA: result = rs1 >> (rs2 & 0x1F); break;
            case ADDI: result = rs1 + imm; break;
            case SLTI: result = (rs1 < imm) ? 1 : 0; break;
            case SLTIU: result = (Integer.compareUnsigned(rs1, imm) < 0) ? 1 : 0; break;
            case ANDI: result = rs1 & imm; break;
            case ORI: result = rs1 | imm; break;
            case XORI: result = rs1 ^ imm; break;
            case SLLI: result = rs1 << (imm & 0x1F); break;
            case SRLI: result = rs1 >>> (imm & 0x1F); break;
            case SRAI: result = rs1 >> (imm & 0x1F); break;
            case LUI: result = imm; break;
            case AUIPC: result = getPC() + imm; break;
            case LB: result = memory.loadByte(rs1 + imm); break;
            case LBU: result = memory.loadByte(rs1 + imm) & 0xFF; break;
            case LH: result = memory.loadHalf(rs1 + imm); break;
            case LHU: result = memory.loadHalf(rs1 + imm) & 0xFFFF; break;
            case LW: result = memory.loadWord(rs1 + imm); break;
            case SB: memory.storeByte(rs1 + imm, (byte) (rs2 & 0xFF)); break;
            case SH: memory.storeHalf(rs1 + imm, (byte) (rs2 & 0xFFFF)); break;
            case SW: memory.storeWord(rs1 + imm, (byte) rs2); break;
            case JAL: setRegister(instr.rd, getPC() + 4); addPC(imm);
                return false;
            case JALR: setRegister(instr.rd, getPC() + 4); setPC((rs1 + imm) & ~1); return false;
            case BEQ: if (rs1 == rs2){ addPC(imm); return false;} break;
            case BNE: if (rs1 != rs2){ addPC(imm); return false;} break;
            case BLT: if (rs1 < rs2){ addPC(imm); return false;} break;
            case BLTU: if (Integer.compareUnsigned(rs1, rs2) < 0){ addPC(imm); return false;} break;
            case BGE: if  (rs1 >= rs2) {addPC(imm); return false;} break;
            case BGEU: if (Integer.compareUnsigned(rs1, rs2) >= 0){ addPC(imm);return false;} break;
            default:
                throw new IllegalArgumentException("Unknown instruction: " + instr.op);
        }
        stepPC();
        setRegister(instr.rd, result);
        return false;
    }

    public void loadProgram(int[] instructions, int baseAddress) {
        for (int i = 0; i < instructions.length; i++) {
            memory.storeWord(baseAddress + i * 4, instructions[i]);
        }
        pc = baseAddress;
    }

    public int getPC() {
        return pc;
    }

    public void setPC(int newPC) {
        pc = newPC;
    }

    public void addPC(int offset) {
        pc += offset;
    }

    public void stepPC() {
        pc += 4;
    }

    public void setRegister(int index, int value) {
        if (index != 0) registers[index] = value;
    }

    public void printRegisters() {
        for (int i = 0; i < 32; i++) {
            System.out.printf("x%d = %d\n", i, registers[i]);
        }
    }

    public Instruction fetchAndDecode() {
        int instrWord = memory.loadWord(pc);
        return Instruction.decode(instrWord);
    }



}