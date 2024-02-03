import { useInstall } from "@work/utils";
import index from "./src/index.vue";

export { type Paging } from "./src/index.vue";

export const Pagination = useInstall(index);

export default index;
