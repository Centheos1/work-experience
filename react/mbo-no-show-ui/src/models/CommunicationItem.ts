export interface CommunicationItem {
    id: string;
    type: string;
    method: string;
    body: string
}

export function toCommunicationItem(doc): CommunicationItem {
    return { id: doc.id, ...doc.data() };
}