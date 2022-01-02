export interface Permission {
    label: string;
    rank: number;
}

export interface Credential {
    firstName: string;
    lastName: string;
    email: string;
    userId: string;
    status: string;
    permission: string;
    credentialId?: string
}

// This is the setter
export function toCredential(doc): Credential {
    return { credentialId: doc.id, ...doc.data() };
}