export interface Report {
    updateDate: string; //isoformat time
    name: string;
    siteId: number;
    class_performance_data: PerformanceData[];
    instructor_performance_data: PerformanceData[];
    table_data: LocationPerformanceData[];
    visualisation_data: VisualisationData[]
}

export interface LocationPerformanceData {
    index: string;
    total_classes: number;
    capacity: number;
    total_booked: number;
    total_visited: number;
    signed_in: number;
    no_show: number;
    late_cancel: number;
    total_forgive: number;
    no_show_charges: number;
    no_show_charge_pending: number;
    no_show_dispute_approved: number;
    no_show_refund: number;
    no_show_error: number;
    late_cancel_charges: number;
    late_cancel_charge_pending: number;
    late_cancel_dispute_approved: number;
    late_cancel_refund: number;
    late_cancel_error: number;
    total_charges: number;
    avg_charge_per_member: number;
    no_show_revenue: number;
    late_cancel_revenue: number;
    total_revenue: number;
    percent_no_show: number;
    percent_late_cancel: number;
    percent_capacity_attendance: number;
}

export interface PerformanceData {
    index: string;
    total_classes: number;
    average_class_size: number;
    capacity: number;
    unique_visits: number;
    total_visited: number;
    total_booked: number;
    signed_in: number;
    no_show: number;
    late_cancel: number;
    percent_no_show: number;
    percent_late_cancel: number;
    percent_capacity_attendance: number;
    performance_comparitor: number;
}

export interface VisualisationData {
    index: string;
    percent_capacity_attendance: number;
    percent_late_cancel: number;
    percent_no_show: number;
}

// This is the setter
export function toLocationPerformance(doc): LocationPerformanceData {
    return { id: doc.id, ...doc.data() };
}

// This is the setter
export function toPerformance(doc): PerformanceData {
    return { id: doc.id, ...doc.data() };
}

// This is the setter
export function toVisualisation(doc): VisualisationData {
    return { id: doc.id, ...doc.data() };
}

// This is the setter
export function toReport(doc): Report {
    return { id: doc.id, ...doc.data() };
}

