import { type TypedUseSelectorHook, useSelector } from 'react-redux';
import type { StoreSchema } from 'app/providers/StoreProvider/config/store';

export const useAppSelector: TypedUseSelectorHook<StoreSchema> = useSelector;
