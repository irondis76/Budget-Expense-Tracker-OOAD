document.addEventListener('DOMContentLoaded', function () {
    if (typeof Chart !== 'undefined' && document.getElementById('budgetChart')) {
        const ctx = document.getElementById('budgetChart').getContext('2d');
        const data = JSON.parse(document.getElementById('chartData').textContent);

        new Chart(ctx, {
            type: 'pie',
            data: {
                labels: data.labels,
                datasets: [{
                    label: 'Budget vs Spent',
                    data: data.values,
                    backgroundColor: ['#4caf50', '#f44336'],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'bottom'
                    }
                }
            }
        });
    }
});
