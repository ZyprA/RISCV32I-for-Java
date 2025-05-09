

public class Instruction {

    public enum Type {
        R_TYPE,I_TYPE,S_TYPE,B_TYPE,U_TYPE,J_TYPE
    }

    public Operand op;
    public Type type;
    public int rd, funct3, rs1, rs2, funct7, imm;

    public Instruction(Operand op, Type type, int rd, int funct3, int rs1, int rs2, int funct7, int imm ) {
        this.op = op;
        this.type = type;
        this.rd = rd;
        this.funct3 = funct3;
        this.rs1 = rs1;
        this.rs2 = rs2;
        this.funct7 = funct7;
        this.imm = imm;
    }

    public static Instruction decode(int instructionWord) {
        int opcode = instructionWord & 0x7F;
        int rd = (instructionWord >> 7) & 0x1F;
        int funct3 = (instructionWord >> 12) & 0x7;
        int rs1 = (instructionWord >> 15) & 0x1F;
        int rs2 = (instructionWord >> 20) & 0x1F;
        int funct7 = (instructionWord >> 25) & 0x7F;
        int imm = 0;
        Operand op = null;
        Type32 optype = Type32.fromOpcode(opcode);
        if (optype == null) return null;
        switch (optype) {
            case R_TYPE_REG: // R-type of execution
                if (funct7 == 0x0) {
                    op = switch (funct3) {
                        case 0x0 -> Operand.ADD;
                        case 0x2 -> Operand.SLT;
                        case 0x3 -> Operand.SLTU;
                        case 0x7 -> Operand.AND;
                        case 0x6 -> Operand.OR;
                        case 0x4 -> Operand.XOR;
                        case 0x1 -> Operand.SLL;
                        case 0x5 -> Operand.SRL;
                        default -> op;
                    };
                } else if (funct7 == 0x20) {
                    op = switch (funct3) {
                        case 0x0 -> Operand.SUB;
                        case 0x5 -> Operand.SRA;
                        default -> null;
                    };
                }
                return new Instruction(op, Type.R_TYPE, rd, funct3, rs1, rs2, funct7, imm);
            case I_TYPE_IMM: // I-type of execution
                imm = (instructionWord >> 20) & 0xfff;
                op = switch(funct3) {
                    case 0x0 -> Operand.ADDI;
                    case 0x2 -> Operand.SLTI;
                    case 0x3 -> Operand.SLTIU;
                    case 0x7 -> Operand.ANDI;
                    case 0x6 -> Operand.ORI;
                    case 0x4 -> Operand.XORI;
                    default -> null;
                };
                int imm11_5 = (imm >> 4) & 0x7f;
                if (funct3 == 0x1 && imm11_5 == 0x0) {
                    op = Operand.SLLI;
                } else if (funct3 == 0x5) {
                    op = switch(imm11_5) {
                        case 0x0 -> Operand.SRLI;
                        case 0x20 -> Operand.SRAI;
                        default -> null;
                    };
                }
                return new Instruction(op, Type.I_TYPE, rd, funct3, rs1, 0,  0, imm);
            case U_TYPE_LUI: // LUI U-type of execution
                imm = (instructionWord >> 12) & 0xfffff;
                return new Instruction(Operand.LUI, Type.U_TYPE, rd, 0, 0, 0, 0, imm);
            case U_TYPE_AUIPC: // AUIPC U-type of execution
                imm = (instructionWord >> 12) & 0xfffff;
                return new Instruction(Operand.AUIPC, Type.U_TYPE, rd, 0, 0, 0, 0, imm);
            case I_TYPE_MEM: // I-type of memory access
                imm = (instructionWord >> 20) & 0xfff;
                op = switch(funct3) {
                    case 0x0 -> Operand.LB;
                    case 0x4 -> Operand.LBU;
                    case 0x1 -> Operand.LH;
                    case 0x5 -> Operand.LHU;
                    case 0x2 -> Operand.LW;
                    default -> null;
                };
                return new Instruction(op, Type.I_TYPE, rd, funct3, rs1, 0, 0, imm);
            case S_TYPE_MEM: // S-type of memory access
                int imm11_5b = (instructionWord >> 20) & 0xef;
                int imm4_0 = (instructionWord >> 7) & 0x1f;
                imm = (imm11_5b << 5) | imm4_0;
                op = switch(funct3) {
                    case 0x0 -> Operand.SB;
                    case 0x1 -> Operand.SH;
                    case 0x2 -> Operand.SW;
                    default -> null;
                };
                return new Instruction(op, Type.S_TYPE, 0, funct3, rs1, rs2, 0, imm);
            case J_TYPE_PROG: // J-type of program control
                int imm20 = (instructionWord >> 31) & 0x1;
                int imm10_1 = (instructionWord >> 21) & 0x3ff;
                int imm11 = (instructionWord >> 20) & 0x1;
                int imm19_12 = (instructionWord >> 12) & 0xff;
                imm = (imm20 << 20) | (imm19_12 << 12) | (imm11 << 11) | (imm10_1 << 1);
                return new Instruction(Operand.JAL, Type.J_TYPE, rd, 0, 0, 0, 0, imm);
            case I_TYPE_PROG: // I-type of program control
                imm = (instructionWord >> 20) & 0xfff;
                if (funct3 == 0x0) {
                    return new Instruction(Operand.JALR, Type.I_TYPE, rd, funct3, rs1, 0, 0, imm);
                }
                return null;
            case B_TYPE_PROG: // B-type of program control
                int imm12 = (instructionWord >> 31) & 0x1;
                int imm11_b = (instructionWord >> 7) & 0x1;
                int imm10_5 = (instructionWord >> 25) & 0x3f;
                int imm4_1 = (instructionWord >> 8) & 0xf;
                imm = (imm12 << 12) | (imm11_b << 11) | (imm10_5 << 5) | (imm4_1 << 1);
                op = switch(funct3) {
                    case 0x0 -> Operand.BEQ;
                    case 0x1 -> Operand.BNE;
                    case 0x4 -> Operand.BLT;
                    case 0x6 -> Operand.BLTU;
                    case 0x5 -> Operand.BGE;
                    case 0x7 -> Operand.BGEU;
                    default -> null;
                };

                return new Instruction(op, Type.B_TYPE, 0, funct3, rs1, rs2, 0, imm);
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return String.format("%s rd=%d rs1=%d rs2=%d imm=%d", op.toString(), rd, rs1, rs2, imm);
    }
}
