import { type TypedUseSelectorHook, useSelector } from 'react-redux';
import { type StoreSchema } from 'app/providers/StoreProvider';

export const useAppSelector: TypedUseSelectorHook<StoreSchema> = useSelector;
