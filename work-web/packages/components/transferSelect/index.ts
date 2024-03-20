import { useInstall } from "@work/utils";
import index from "./src/index.vue";

export type { TransferSelectProps, TransferTableColumn } from "./src/index.vue";

export const TransferSelect = useInstall(index);

export default index;
