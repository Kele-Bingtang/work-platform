import { useInstall } from "@work/utils";
import index from "./src/index.vue";
export type { ProFormProps, ProFormOnEmits } from "./src/index.vue";
import Tree from "./src/components/Tree.vue";
import ProFormItem from "./src/components/ProFormItem.vue";

export * from "./src/interface";
export * from "./src/helper";
export { useProForm } from "./src/hooks/useProForm";

export { Tree, ProFormItem };

export var ProForm = useInstall(index);

export default index;
