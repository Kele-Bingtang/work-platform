import { useInstall } from "@work/utils";
import index from "./src/index.vue";
export type { ListCardInstance } from "./src/index.vue";

export const ListCard = useInstall(index);

export default index;
