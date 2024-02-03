import { useInstall } from "@work/utils";
import index from "./src/index.vue";

export * from "./src/interface";

export const ProForm = useInstall(index);

export default index;
