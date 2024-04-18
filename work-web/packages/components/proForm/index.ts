import { useInstall } from "@work/utils";
import index from "./src/index.vue";
import Tree from "./src/components/Tree.vue";

export * from "./src/interface";

export { Tree };

export const ProForm = useInstall(index);

export default index;
