import { useInstall } from "@work/utils";
import index from "./src/index.vue";
export type {
  ProSearchProps,
  ProSearchSchemaProps,
  ActionPosition,
  ProSearchExpose,
  ProSearchOnEmits,
} from "./src/index.vue";

export { useProSearch } from "./src/hooks/useProSearch";

export const ProSearch = useInstall(index);

export type ProSearchInstance = InstanceType<typeof index>;

export default index;
