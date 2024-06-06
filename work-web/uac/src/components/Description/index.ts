import { useInstall } from "@work/utils";
import index from "./src/index.vue";
export type { DescriptionProps } from "./src/index.vue";

export const Description = useInstall(index);

export default index;
