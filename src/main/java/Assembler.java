public class Assembler{
    public static int ADD(int rd, int rs1, int rs2) {
        return RTYPE(Operand.ADD, rd, rs1, rs2);
    }

    public static int SUB(int rd, int rs1, int rs2) {
        return RTYPE(Operand.SUB, rd, rs1, rs2);
    }

    public static int SLT(int rd, int rs1, int rs2) {
        return RTYPE(Operand.SLT, rd, rs1, rs2);
    }

    public static int AND(int rd, int rs1, int rs2) {
        return RTYPE(Operand.AND, rd, rs1, rs2);
    }

    public static int OR(int rd, int rs1, int rs2) {
        return RTYPE(Operand.OR, rd, rs1, rs2);
    }

    public static int XOR(int rd, int rs1, int rs2) {
        return RTYPE(Operand.XOR, rd, rs1, rs2);
    }

    public static int SLL(int rd, int rs1, int rs2) {
        return RTYPE(Operand.SLL, rd, rs1, rs2);
    }
    public static int SRL(int rd, int rs1, int rs2) {
        return RTYPE(Operand.SRL, rd, rs1, rs2);
    }

    public static int SRA(int rd, int rs1, int rs2) {
        return RTYPE(Operand.SRA, rd, rs1, rs2);
    }

    public static int ADDI(int rd, int rs1, int imm) {
        return ITYPE(Operand.ADDI, rd, rs1, imm);
    }

    public static int SLTI(int rd, int rs1, int imm) {
        return ITYPE(Operand.SLTI, rd, rs1, imm);
    }

    public static int SLTIU(int rd, int rs1, int imm) {
        return ITYPE(Operand.SLTIU, rd, rs1, imm);
    }

    public static int ANDI(int rd, int rs1, int imm) {
        return ITYPE(Operand.ANDI, rd, rs1, imm);
    }

    public static int ORI(int rd, int rs1, int imm) {
        return ITYPE(Operand.ORI, rd, rs1, imm);
    }

    public static int XORI(int rd, int rs1, int imm) {
        return ITYPE(Operand.XORI, rd, rs1, imm);
    }

    public static int SLLI(int rd, int rs1, int imm) {
        return ITYPE(Operand.SLLI, rd, rs1, imm);
    }

    public static int SRLI(int rd, int rs1, int imm) {
        return ITYPE(Operand.SRLI, rd, rs1, imm);
    }

    public static int SRAI(int rd, int rs1, int imm) {
        return ITYPE(Operand.SRAI, rd, rs1, imm);
    }

    public static int LUI(int rd, int imm) {
        return UTYPE(Operand.LUI, rd, imm);
    }

    public static int AUIPC(int rd, int imm) {
        return UTYPE(Operand.AUIPC, rd, imm);
    }

    public static int LB(int rd, int rs1, int imm) {
        return ITYPE(Operand.LB, rd, rs1, imm);
    }

    public static int LBU(int rd, int rs1, int imm) {
        return ITYPE(Operand.LBU, rd, rs1, imm);
    }

    public static int LH(int rd, int rs1, int imm) {
        return ITYPE(Operand.LH, rd, rs1, imm);
    }

    public static int LHU(int rd, int rs1, int imm) {
        return ITYPE(Operand.LHU, rd, rs1, imm);
    }

    public static int LW(int rd, int rs1, int imm) {
        return ITYPE(Operand.LW, rd, rs1, imm);
    }

    public static int SB(int rs1, int rs2, int imm) {
        return STYPE(Operand.SB, rs1, rs2, imm);
    }

    public static int SH(int rs1, int rs2, int imm) {
        return STYPE(Operand.SH, rs1, rs2, imm);
    }

    public static int SW(int rs1, int rs2, int imm) {
        return STYPE(Operand.SW, rs1, rs2, imm);
    }

    public static int JAL(int rd, int imm) {
        return JTYPE(Operand.JAL, rd, imm);
    }

    public static int JALR(int rd, int rs1, int imm) {
        return ITYPE(Operand.JALR, rd, rs1, imm);
    }

    public static int BEQ(int rs1, int rs2, int imm) {
        return BTYPE(Operand.BEQ, rs1, rs2, imm);
    }

    public static int BNE(int rs1, int rs2, int imm) {
        return BTYPE(Operand.BNE, rs1, rs2, imm);
    }

    public static int BLT(int rs1, int rs2, int imm) {
        return BTYPE(Operand.BLT, rs1, rs2, imm);
    }

    public static int BLTU(int rs1, int rs2, int imm) {
        return BTYPE(Operand.BLTU, rs1, rs2, imm);
    }

    public static int BGE(int rs1, int rs2, int imm) {
        return BTYPE(Operand.BGE, rs1, rs2, imm);
    }

    public static int BGEU(int rs1, int rs2, int imm) {
        return BTYPE(Operand.BGEU, rs1, rs2, imm);
    }





    public static int RTYPE(Operand op, int rd, int rs1, int rs2) {
        return RTYPE(op.optype, rd, op.funct3, rs1, rs2, op.funct7);
    }

    private static int RTYPE(int opcode, int rd, int funct3, int rs1, int rs2, int funct7) {
        return (funct7 << 25) | (rs2 << 20) | (rs1 << 15) |
                (funct3 << 12) | (rd << 7) | opcode;
    }

    public static int ITYPE(Operand op, int rd, int rs1, int imm) {
        return ITYPE(op.optype, rd, op.funct3, rs1, imm);
    }

    private static int ITYPE(int opcode, int rd, int funct3, int rs1, int imm) {
        return (imm << 20) | (rs1 << 15) |
                (funct3 << 12) | (rd << 7) | opcode;
    }

    public static int STYPE(Operand op, int rs1,  int rs2, int imm) {
        return STYPE(op.optype, op.funct3, rs1, rs2, imm);
    }

    private static int STYPE(int opcode, int funct3, int rs1, int rs2, int imm) {
        int imm31_25 = getBitRange(imm, 5, 11);
        int imm11_7 = getBitRange(imm, 0, 4);
        return (imm31_25 << 25) | (imm11_7 << 7) | (rs2 << 20) | (rs1 << 15) |
                (funct3 << 12) | opcode;
    }

    public static int BTYPE(Operand op, int rs1, int rs2, int imm) {
        return BTYPE(op.optype, op.funct3, rs1, rs2, imm);
    }

    private static int BTYPE(int opcode, int funct3, int rs1, int rs2, int imm) {
        int imm31 = getBitRange(imm, 12, 12);
        int imm30_25 = getBitRange(imm, 5, 10);
        int imm11_8 = getBitRange(imm, 1, 4);
        int imm7 = getBitRange(imm, 11, 11);
        return (imm31 << 31) | (imm30_25 << 25) | (imm11_8 << 8) | (imm7 << 7) | (rs2 << 20) | (rs1 << 15) |
                (funct3 << 12) | opcode;
    }

    public static int JTYPE(Operand op, int rd, int imm) {
        return JTYPE(op.optype, rd, imm);
    }

    private static int JTYPE(int opcode, int rd, int imm) {
        int imm31 = getBitRange(imm, 20, 20);
        int imm30_21 = getBitRange(imm, 1, 10);
        int imm20 = getBitRange(imm, 11, 11);
        int imm19_12 = getBitRange(imm, 12, 19);

        return (imm31 << 31) | (imm30_21 << 21) | (imm20 << 20) | (imm19_12 << 12) | (rd << 7) | opcode;
    }

    public static int UTYPE(Operand op, int rd, int imm) {
        return UTYPE(op.optype, rd, imm);
    }

    private static int UTYPE(int opcode, int rd, int imm) {
        int imm31_12 = getBitRange(imm, 12,30);

        return (imm31_12 << 12) | (rd << 7) | opcode;
    }

    private static int getBitRange(int value, int start, int end) {
        return (value >> start) & ((1 << (end - start + 1)) - 1);
    }
}
