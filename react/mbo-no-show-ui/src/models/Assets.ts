export interface Asset {
    fullPath: string;
    type: string;
    downloadUrl: string;
    name: string;
}

export interface Assets {
    id: string;
    images: Asset[];
    media: Asset[]
}

export function toAssets(doc): Assets {
    // console.log('toAsset: ', doc.data())
    return { id: doc.id, ...doc.data() };
}

export function toAsset(doc): Asset {
    return { id: doc.id, ...doc.data() };
}