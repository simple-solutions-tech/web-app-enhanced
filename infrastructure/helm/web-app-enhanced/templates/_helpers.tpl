{{/*
Expand the chart name.
*/}}
{{- define "web-app-enhanced.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Create a default fully qualified app name.
*/}}
{{- define "web-app-enhanced.fullname" -}}
{{- if .Values.fullnameOverride }}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- printf "%s-%s" .Release.Name .Chart.Name | trunc 63 | trimSuffix "-" }}
{{- end }}
{{- end }}

{{/*
Common labels applied to every resource.
*/}}
{{- define "web-app-enhanced.labels" -}}
helm.sh/chart: {{ .Chart.Name }}-{{ .Chart.Version }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{/*
Selector labels for the backend.
*/}}
{{- define "web-app-enhanced.backend.selectorLabels" -}}
app.kubernetes.io/name: {{ include "web-app-enhanced.name" . }}-backend
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{/*
Selector labels for the frontend.
*/}}
{{- define "web-app-enhanced.frontend.selectorLabels" -}}
app.kubernetes.io/name: {{ include "web-app-enhanced.name" . }}-frontend
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}
