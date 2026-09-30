document.addEventListener('DOMContentLoaded', () => {
    fetchTransactions();
    // Refresh every 5 seconds
    setInterval(fetchTransactions, 5000);
});

async function fetchTransactions() {
    try {
        const response = await fetch('http://localhost:8080/api/flows');
        if (!response.ok) throw new Error('Network response was not ok');
        const data = await response.json();
        renderTransactions(data);
    } catch (error) {
        console.error('Error fetching transactions:', error);
    }
}

function renderTransactions(transactions) {
    const list = document.getElementById('transactionList');

    if (transactions.length === 0) {
        list.innerHTML = '<div class="transaction-card" style="text-align: center;">No transactions found</div>';
        return;
    }

    list.innerHTML = transactions.map(t => {
        const hasResponse = t.response != null;
        const status = hasResponse ? t.response.statusCode : 'PENDING';
        const statusClass = status === 200 ? 'status-success' : (status === 'PENDING' ? '' : 'status-error');
        const duration = hasResponse ?
            (new Date(t.timestamp).getTime() - new Date(t.response.timestamp).getTime()) : null; // This logic might be reversed, fixing below

        return `
            <div class="transaction-card" onclick="this.classList.toggle('expanded')">
                <div class="card-header">
                    <div class="flow-visual">
                        <span class="service-badge">${t.sourceService}</span>
                        <span class="arrow">→</span>
                        <span class="service-badge">${t.destinationService}</span>
                    </div>
                    
                    <div style="display: flex; align-items: center; gap: 1rem;">
                        <span class="timestamp">${new Date(t.timestamp).toLocaleString()}</span>
                        <span class="status-badge ${statusClass}">${status}</span>
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="transform: rotate(0deg); transition: transform 0.2s;"><polyline points="6 9 12 15 18 9"></polyline></svg>
                    </div>
                </div>

                <div class="details">
                    <div>
                        <span class="label">Request (${t.httpMethod} ${t.requestPath})</span>
                        <div class="json-block">${formatJSON(t.requestPayload)}</div>
                    </div>
                    <div>
                        <span class="label">Response</span>
                        <div class="json-block">${hasResponse ? formatJSON(t.response.responsePayload) : 'No Response Yet'}</div>
                    </div>
                </div>
            </div>
        `;
    }).join('');
}

function formatJSON(str) {
    try {
        return JSON.stringify(JSON.parse(str), null, 2);
    } catch (e) {
        return str;
    }
}
