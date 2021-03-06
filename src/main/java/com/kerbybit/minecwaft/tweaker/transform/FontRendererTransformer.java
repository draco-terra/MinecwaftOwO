package com.kerbybit.minecwaft.tweaker.transform;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class FontRendererTransformer implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{"net.minecraft.client.gui.FontRenderer"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            String methodName = mapMethodName(classNode, methodNode);

            if (methodName.equalsIgnoreCase("renderStringAtPos")
                    || methodName.equalsIgnoreCase("func_78255_a")
                    || methodName.equalsIgnoreCase("getStringWidth")
                    || methodName.equalsIgnoreCase("func_78256_a")) {
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), changeTextHook());
            }
        }
    }

    private InsnList changeTextHook() {
        InsnList insnList = new InsnList();

        insnList.add(new VarInsnNode(Opcodes.ALOAD, 1));
        insnList.add(new MethodInsnNode(
                Opcodes.INVOKESTATIC,
                "com/kerbybit/minecwaft/MinecwaftOwO",
                "makeOwO",
                "(Ljava/lang/String;)Ljava/lang/String;",
                false
        ));
        insnList.add(new VarInsnNode(Opcodes.ASTORE, 1));

        return insnList;
    }
}
