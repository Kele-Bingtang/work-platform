import { useInstall } from "@work/utils";
import index from "./src/index.vue";

export type { TreeFilterInstance } from "./src/index.vue";

export const TreeFilter = useInstall(index);

export default index;
