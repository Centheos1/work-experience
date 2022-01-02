export function formatDate(isoString) {
    return new Date(isoString).toLocaleDateString('en-US', {
        day: 'numeric', month: 'short', year: 'numeric'
    });
}

export function formatDateTime(isoString) {
    return new Date(isoString).toLocaleDateString('en-US', {
        hour: 'numeric', minute: '2-digit', day: 'numeric', month: 'short', year: 'numeric'
    });
}